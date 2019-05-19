package io.cjf.checkinout0516.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wechatmp", url = "https://api.weixin.qq.com/cgi-bin")
public interface WechatMPApi {

    @GetMapping("/token")
    JSONObject getAccessToken(@RequestParam String grant_type,
                              @RequestParam String appid,
                              @RequestParam String secret);
}
