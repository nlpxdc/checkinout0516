package io.cjf.checkinout0516.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.cjf.checkinout0516.dao.CheckInOutMapper;
import io.cjf.checkinout0516.dto.MessageTextDTO;
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

    @Value("${wechat.mp.id}")
    private String wechatmpId;

    @Autowired
    private CheckInOutMapper checkInOutMapper;

    @GetMapping("/receive")
    public String receive(@RequestParam String echostr){
        logger.info("{}", echostr);
        return echostr;
    }

//    @PostMapping(value = "/receive",produces = MediaType.APPLICATION_XML_VALUE)
    public Object receive(@RequestBody JSONObject message){

        String msgType = message.getString("MsgType");
        if (msgType.equals("event")){
            String event = message.getString("Event");
            if (event.equals("CLICK")){
                String eventKey = message.getString("EventKey");
                if (eventKey.equals("checkin")){
                    logger.info("check in");
                    String openid = message.getString("FromUserName");
                    Position position = openidPosition.get(openid);

                    //todo check if position null
                    if (position == null){
                        MessageTextDTO messageTextDTO = messageTextDTO(openid, "对不起，获取不到位置，无法打卡");
                        return messageTextDTO;
                    }

                    Coordinate lat = Coordinate.fromDegrees(checkLatitude);
                    Coordinate lng = Coordinate.fromDegrees(checkLongitude);
                    Point checkPosition = Point.at(lat, lng);

                    lat = Coordinate.fromDegrees(position.getLatitude());
                    lng = Coordinate.fromDegrees(position.getLongitude());
                    Point userPosition = Point.at(lat, lng);

                    double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
                    if (distance > 300){
                        //todo return message to wechat
                        MessageTextDTO messageTextDTO = messageTextDTO(openid, "对不起，不在打卡范围");
                        return messageTextDTO;
                    }

                    CheckInOut checkInOut = checkInOutMapper.selectRecentByOpenidType(openid, CheckType.CheckIn.ordinal());
                    if (checkInOut == null){
                        checkInOut = new CheckInOut();
                        checkInOut.setOpenid(openid);
                        checkInOut.setType(CheckType.CheckIn.ordinal());
                        checkInOut.setTime(new Date());
                        checkInOutMapper.insert(checkInOut);
                        //todo return thx to wechat
                        MessageTextDTO messageTextDTO = messageTextDTO(openid,"谢谢，打卡成功");
                        return messageTextDTO;

                    }else {
                        Date checkinTime = checkInOut.getTime();
                        long checkinTimestamp = checkinTime.getTime();
                        Date current = new Date();
                        long currentTimestamp = current.getTime();
                        long duration = currentTimestamp - checkinTimestamp;
                        if (duration < 5 * 60 * 1000){
                            logger.info("{} already checkin", openid);
                            //todo return already checkin notification
                        }else {
                            //todo return thx to wechat
                        }
                    }


                }
            }
            if (event.equals("LOCATION")){
                String openid = message.getString("FromUserName");
                Double latitude = message.getDouble("Latitude");
                Double longitude = message.getDouble("Longitude");
                Position position = new Position(latitude, longitude);
                openidPosition.put(openid,position);
                logger.info("set user position: {}, {}",openid, JSON.toJSON(position));
            }

        }
        return null;
    }

    private MessageTextDTO messageTextDTO(String openid, String content){
        MessageTextDTO messageTextDTO = new MessageTextDTO();
        messageTextDTO.setToUserName(openid);
        messageTextDTO.setFromUserName(wechatmpId);
        messageTextDTO.setCreateTime(new Date().getTime());
        messageTextDTO.setContent(content);
        return messageTextDTO;
    }
}
