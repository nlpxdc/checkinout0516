package io.cjf.checkinout0516.controller;

import io.cjf.checkinout0516.constant.WechatReqMsgTypeConstant;
import io.cjf.checkinout0516.dto.WechatMPEventReqMsg;
import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import io.cjf.checkinout0516.handler.EventMsgHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechatmp")
@EnableAutoConfiguration
public class WechatMPController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventMsgHandler eventMsgHandler;

    private WechatMPResMsg resMsg;

    @Value("${wechat.signature.verify.enabled}")
    private boolean signatureVerifyEnabled;

    @GetMapping("/receive")
    public String receive(@RequestParam String signature,
                          @RequestParam Integer timestamp,
                          @RequestParam String nonce,
                          @RequestParam String echostr) {
        logger.info("GET Request!!!");
        logger.info("signature: {}", signature);
        logger.info("timestamp: {}", timestamp);
        logger.info("nonce: {}", nonce);
        logger.info("echostr: {}", echostr);
        //todo verify with token
        return echostr;
    }

    @PostMapping(value = "/receive", produces = MediaType.APPLICATION_XML_VALUE)
    public WechatMPResMsg receive(@RequestParam(required = false) String signature,
                                  @RequestParam(required = false) Integer timestamp,
                                  @RequestParam(required = false) String nonce,
                                  @RequestBody WechatMPReqMsg reqMsg) {

        if (signatureVerifyEnabled){
            //todo verify with token, check from wechat mp
        }

        //todo check duplicate request msg, prevent replay
        //common by msgId
        //event by FromUserName + CreateTime

        //todo check async handle


        logger.info("{}",reqMsg);

        String msgType = reqMsg.getMsgType();
        String fromUserName = reqMsg.getFromUserName();
        Integer createTime = reqMsg.getCreateTime();

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
                WechatMPEventReqMsg eventReqMsg = (WechatMPEventReqMsg) reqMsg;
                WechatMPResMsg resMsg = eventMsgHandler.handle(eventReqMsg);
                this.resMsg = resMsg;
                break;
            default:
                logger.warn("it doesn't match msg type");
        }

        return resMsg;
    }
}
