package io.cjf.checkinout0516.handler;

import com.alibaba.fastjson.JSON;
import io.cjf.checkinout0516.constant.WechatEventConstant;
import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import io.cjf.checkinout0516.service.UserService;
import io.cjf.checkinout0516.vo.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class EventMsgHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    private WechatMPResMsg resMsg;

    public WechatMPResMsg handle(WechatMPReqMsg reqMsg) {
        String event = reqMsg.getString("Event");

        switch (event) {
            case WechatEventConstant.SUBSCRIBE:
                logger.info("receive {}", WechatEventConstant.SUBSCRIBE);
                break;
            case WechatEventConstant.UNSUBSCRIBE:
                logger.info("receive {}", WechatEventConstant.UNSUBSCRIBE);
                break;
            case WechatEventConstant.SCAN:
                logger.info("receive {}", WechatEventConstant.SCAN);
                break;
            case WechatEventConstant.LOCATION:
                logger.info("receive {}", WechatEventConstant.LOCATION);
                handleLocation(reqMsg);
                break;
            case WechatEventConstant.CLICK:
                logger.info("receive {}", WechatEventConstant.CLICK);
                break;
            case WechatEventConstant.VIEW:
                logger.info("receive {}", WechatEventConstant.VIEW);
                break;
            default:
                logger.info("it doesn't match any event");
        }
        return resMsg;
    }

    private void handleLocation(WechatMPReqMsg reqMsg){
        @NotBlank String openId = reqMsg.getFromUserName();
        Double latitude = reqMsg.getDouble("Latitude");
        Double longitude = reqMsg.getDouble("Longitude");
        Position position = new Position(latitude, longitude);
        userService.savePosition(openId, position);
        logger.info("set user position: {}, {}", openId, JSON.toJSONString(position));
    }

}