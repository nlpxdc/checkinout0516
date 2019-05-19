package io.cjf.checkinout0516.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.cjf.checkinout0516.api.WechatMPApi;
import io.cjf.checkinout0516.service.WechatMPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatMPServiceImpl implements WechatMPService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wechatmp.appId}")
    private String appId;

    @Value("${wechatmp.appSecret}")
    private String appSecret;

    @Autowired
    private WechatMPApi wechatMPApi;

    @Override
    public String getAccessToken() {
        logger.info("ready to renew wechatmp access token");
        JSONObject jsonObject = wechatMPApi.getAccessToken("client_credential", appId, appSecret);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }
}
