package io.cjf.checkinout0516.controller;

import io.cjf.checkinout0516.dto.TextResMsg;
import io.cjf.checkinout0516.dto.WechatMPReqMsg;
import io.cjf.checkinout0516.dto.WechatMPResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechatmptest")
public class WechatMPTestController {

    @Autowired
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Long count = 0L;

    @GetMapping("/receive")
    public String receive(@RequestParam(required = false) String signature,
                          @RequestParam(required = false) Long timestamp,
                          @RequestParam(required = false) String nonce,
                          @RequestParam(required = false) String echostr){
        logger.info("GET Request!!!");
        logger.info("signature: {}", signature);
        logger.info("timestamp: {}", timestamp);
        logger.info("nonce: {}", nonce);
        logger.info("echostr: {}", echostr);
        //todo verify with token
        return echostr;
    }

    @PostMapping("/receive")
    public WechatMPResMsg receive(@RequestParam(required = false) String signature,
                                  @RequestParam(required = false) Long timestamp,
                                  @RequestParam(required = false) String nonce,
                                  @RequestParam(required = false) String echostr,
                                  @RequestBody WechatMPReqMsg reqMsg){
        logger.info("POST Request!!!");
        logger.info("signature: {}", signature);
        logger.info("timestamp: {}", timestamp);
        logger.info("nonce: {}", nonce);
        logger.info("echostr: {}", echostr);
        logger.info("message: {}", reqMsg);

        TextResMsg textResMsg = new TextResMsg("oUwXe58JsPM6MBFsI3YvnbFIpg-8",count.toString());
        return textResMsg;
    }
}