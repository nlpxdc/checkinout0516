package io.cjf.checkinout0516.scheduler;

import io.cjf.checkinout0516.component.WechatMPVariable;
import io.cjf.checkinout0516.service.WechatMPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WechatMPScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatMPService wechatMPService;

    @Autowired
    private WechatMPVariable wechatMPVariable;

    //todo config with properties
    @Scheduled(fixedRate = 5400*1000)
    public void refreshAccessToken(){
        logger.info("begin to refresh wechatmp access token");
        String accessToken = wechatMPService.getAccessToken();
        wechatMPVariable.setAccessToken(accessToken);
        logger.info("wechatmp access token has been refreshed: {}", accessToken);
    }
}
