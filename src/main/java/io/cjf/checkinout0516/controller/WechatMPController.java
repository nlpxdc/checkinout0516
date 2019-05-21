package io.cjf.checkinout0516.controller;

import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.exception.ClientException;
import io.cjf.checkinout0516.handler.MsgTypeHandler;
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
    private MsgTypeHandler msgTypeHandler;

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

    @PostMapping(value = "/receive", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE })
    public Object receive(@RequestParam(required = false) String signature,
                                  @RequestParam(required = false) Integer timestamp,
                                  @RequestParam(required = false) String nonce,
                                  @RequestBody WechatMPReqMsg reqMsg) throws ClientException {

        if (signatureVerifyEnabled){
            //todo verify with token, check from wechat mp
        }

        //todo check duplicate request msg, prevent replay
        //common by msgId
        //event by FromUserName + CreateTime

        //todo check async handle

        logger.info("{}",reqMsg);

        Object resMsg = msgTypeHandler.handle(reqMsg);

        return resMsg;
    }
}
