package io.cjf.checkinout0516.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.checkinout0516.vo.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PostMapping(value = "/receive",produces = MediaType.APPLICATION_XML_VALUE)
    public JSONObject receive(@RequestBody JSONObject message){

        String msgType = message.getString("MsgType");
        if (msgType.equals("event")){
            String event = message.getString("Event");
            if (event.equals("CLICK")){
                String eventKey = message.getString("EventKey");
                if (eventKey.equals("checkin")){
                    logger.info("check in");
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
