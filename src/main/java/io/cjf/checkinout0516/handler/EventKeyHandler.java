package io.cjf.checkinout0516.handler;

import io.cjf.checkinout0516.constant.WechatEventKeyConstant;
import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventKeyHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private WechatMPResMsg resMsg;

    public WechatMPResMsg handle(WechatMPReqMsg reqMsg) {
        String eventKey = reqMsg.getString("EventKey");

        switch (eventKey) {
            case WechatEventKeyConstant.CHECK_IN:
                logger.info("receive {}", WechatEventKeyConstant.CHECK_IN);
                break;
            default:
                logger.info("it doesn't match any event key");
        }

        return resMsg;
    }

    private WechatMPResMsg handleCheckIn(WechatMPReqMsg reqMsg){
        return null;
    }
}
