package io.cjf.checkinout0516.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cjf.checkinout0516.constant.WechatReqMsgTypeConstant;
import io.cjf.checkinout0516.dto.WechatMPEventReqMsg;
import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import io.cjf.checkinout0516.exception.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class MsgTypeHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventMsgHandler eventMsgHandler;

    private WechatMPResMsg resMsg;

    public WechatMPResMsg handle(WechatMPReqMsg reqMsg) throws ClientException {
        @NotBlank String msgType = reqMsg.getMsgType();
        switch (msgType) {
            case WechatReqMsgTypeConstant.TEXT:
                logger.info("receive {}", WechatReqMsgTypeConstant.TEXT);
                break;
            case WechatReqMsgTypeConstant.IMAGE:
                logger.info("receive {}", WechatReqMsgTypeConstant.IMAGE);
                break;
            case WechatReqMsgTypeConstant.VOICE:
                logger.info("receive {}", WechatReqMsgTypeConstant.VOICE);
                break;
            case WechatReqMsgTypeConstant.VIDEO:
                logger.info("receive {}", WechatReqMsgTypeConstant.VIDEO);
                break;
            case WechatReqMsgTypeConstant.SHORT_VIDEO:
                logger.info("receive {}", WechatReqMsgTypeConstant.SHORT_VIDEO);
                break;
            case WechatReqMsgTypeConstant.LOCATION:
                logger.info("receive {}", WechatReqMsgTypeConstant.LOCATION);
                break;
            case WechatReqMsgTypeConstant.LINK:
                logger.info("receive {}", WechatReqMsgTypeConstant.LINK);
                break;
            case WechatReqMsgTypeConstant.EVENT:
                logger.info("receive {}", WechatReqMsgTypeConstant.EVENT);
                String reqMsgJsonStr = reqMsg.toJSONString();
                WechatMPEventReqMsg eventReqMsg = JSON.parseObject(reqMsgJsonStr, WechatMPEventReqMsg.class);
                WechatMPResMsg resMsg = eventMsgHandler.handle(eventReqMsg);
                this.resMsg = resMsg;
                break;
            default:
                logger.warn("it doesn't match msg type");
        }
        return resMsg;
    }
}
