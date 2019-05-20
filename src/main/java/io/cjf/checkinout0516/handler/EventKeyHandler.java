package io.cjf.checkinout0516.handler;

import io.cjf.checkinout0516.constant.WechatEventKeyConstant;
import io.cjf.checkinout0516.dto.TextResMsg;
import io.cjf.checkinout0516.dto.WechatMPEventReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class EventKeyHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    private WechatMPResMsg resMsg;

    public WechatMPResMsg handle(WechatMPEventReqMsg reqMsg) throws ClientException {
        String eventKey = reqMsg.getString("EventKey");

        switch (eventKey) {
            case WechatEventKeyConstant.CHECK_IN:
                logger.info("receive {}", WechatEventKeyConstant.CHECK_IN);
                resMsg = handleCheckIn(reqMsg);
                break;
            default:
                logger.info("it doesn't match any event key");
        }

        return resMsg;
    }

    private TextResMsg handleCheckIn(WechatMPEventReqMsg reqMsg) throws ClientException {
        @NotBlank String openid = reqMsg.getFromUserName();
        userService.checkIn(openid);
        TextResMsg textResMsg = new TextResMsg(openid, "thx, check in succeed");
        return textResMsg;
    }
}
