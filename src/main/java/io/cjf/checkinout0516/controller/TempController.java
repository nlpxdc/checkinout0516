package io.cjf.checkinout0516.controller;

import io.cjf.checkinout0516.service.WechatMPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
public class TempController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatMPService wechatMPService;

    @GetMapping("/test")
    public String test(){
        String accessToken = wechatMPService.getAccessToken();
        return "temptest";
    }
}
