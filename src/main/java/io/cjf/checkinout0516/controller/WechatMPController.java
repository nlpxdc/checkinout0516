package io.cjf.checkinout0516.controller;

import com.alibaba.fastjson.JSON;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.cjf.checkinout0516.dao.CheckInOutMapper;
import io.cjf.checkinout0516.dto.TextResMsg;
import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import io.cjf.checkinout0516.enumeration.CheckType;
import io.cjf.checkinout0516.po.CheckInOut;
import io.cjf.checkinout0516.vo.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/wechatmp")
@EnableAutoConfiguration
public class WechatMPController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashMap<String, Position> openidPosition = new HashMap<>();

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @Autowired
    private CheckInOutMapper checkInOutMapper;

    @GetMapping("/receive")
    public String receive(@RequestParam String signature,
                          @RequestParam Integer timestamp,
                          @RequestParam String nonce,
                          @RequestParam String echostr){
        logger.info("GET Request!!!");
        logger.info("signature: {}", signature);
        logger.info("timestamp: {}", timestamp);
        logger.info("nonce: {}", nonce);
        logger.info("echostr: {}", echostr);
        //todo verify with token
        return echostr;
    }

    @PostMapping(value = "/receive",produces = MediaType.APPLICATION_XML_VALUE)
    public WechatMPResMsg receive(@RequestBody WechatMPReqMsg reqMsg){

        String msgType = reqMsg.getMsgType();
        Long msgId = reqMsg.getMsgId();
        String fromUserName = reqMsg.getFromUserName();
        Long createTime = reqMsg.getCreateTime();


        if (msgType.equals("event")){
            String event = reqMsg.getString("Event");
            if (event.equals("CLICK")){
                String eventKey = reqMsg.getString("EventKey");
                if (eventKey.equals("checkin")){
                    logger.info("check in");
                    String openid = fromUserName;
                    Position position = openidPosition.get(openid);

                    if (position == null){
                        TextResMsg textResMsg = new TextResMsg(openid, "对不起，获取不到位置，无法打卡");
                        return textResMsg;
                    }

                    Coordinate lat = Coordinate.fromDegrees(checkLatitude);
                    Coordinate lng = Coordinate.fromDegrees(checkLongitude);
                    Point checkPosition = Point.at(lat, lng);

                    lat = Coordinate.fromDegrees(position.getLatitude());
                    lng = Coordinate.fromDegrees(position.getLongitude());
                    Point userPosition = Point.at(lat, lng);

                    double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
                    if (distance > 300){
                        TextResMsg textResMsg = new TextResMsg(openid, "对不起，不在打卡范围");
                        return textResMsg;
                    }

                    CheckInOut checkInOut = checkInOutMapper.selectRecentByOpenidType(openid, CheckType.CheckIn.ordinal());
                    if (checkInOut == null){
                        checkInOut = new CheckInOut();
                        checkInOut.setOpenid(openid);
                        checkInOut.setType(CheckType.CheckIn.ordinal());
                        checkInOut.setTime(new Date());
                        checkInOutMapper.insert(checkInOut);
                        openidPosition.remove(openid);

                        TextResMsg textResMsg = new TextResMsg(openid, "谢谢，打卡成功");
                        return textResMsg;

                    }else {
                        Date checkinTime = checkInOut.getTime();
                        long checkinTimestamp = checkinTime.getTime();
                        Date current = new Date();
                        long currentTimestamp = current.getTime();
                        long duration = currentTimestamp - checkinTimestamp;
                        if (duration < 5 * 60 * 1000){
                            logger.info("{} already checkin", openid);
                            TextResMsg textResMsg = new TextResMsg(openid, "已签到");
                            return textResMsg;
                        }
                    }
                }
            }
            if (event.equals("LOCATION")){
                String openid = fromUserName;
                Double latitude = reqMsg.getDouble("Latitude");
                Double longitude = reqMsg.getDouble("Longitude");
                Position position = new Position(latitude, longitude);
                openidPosition.put(openid,position);
                logger.info("set user position: {}, {}",openid, JSON.toJSON(position));
            }

        }
        return null;
    }
}
