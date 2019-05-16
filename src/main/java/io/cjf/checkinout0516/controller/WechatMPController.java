package io.cjf.checkinout0516.controller;

import com.alibaba.fastjson.JSONObject;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.cjf.checkinout0516.vo.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/wechatmp")
public class WechatMPController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashMap<String, Position> openidPosition = new HashMap<>();

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @PostMapping(value = "/receive",produces = MediaType.APPLICATION_XML_VALUE)
    public JSONObject receive(@RequestBody JSONObject message){

        String msgType = message.getString("MsgType");
        if (msgType.equals("event")){
            String event = message.getString("Event");
            if (event.equals("CLICK")){
                String eventKey = message.getString("EventKey");
                if (eventKey.equals("checkin")){
                    logger.info("check in");
                    String openid = message.getString("FromUserName");
                    Position position = openidPosition.get(openid);

                    Coordinate lat = Coordinate.fromDegrees(checkLatitude);
                    Coordinate lng = Coordinate.fromDegrees(checkLongitude);
                    Point checkPosition = Point.at(lat, lng);

                    lat = Coordinate.fromDegrees(position.getLatitude());
                    lng = Coordinate.fromDegrees(position.getLongitude());
                    Point userPosition = Point.at(lat, lng);

                    double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
                    if (distance > 300){
                        //todo return message to wechat
                    }


                }
            }
            if (event.equals("LOCATION")){
                String openid = message.getString("FromUserName");
                Double latitude = message.getDouble("Latitude");
                Double longitude = message.getDouble("Longitude");
                Position position = new Position(latitude, longitude);
                openidPosition.put(openid,position);
            }

        }
        return null;
    }
}
