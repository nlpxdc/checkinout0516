package io.cjf.checkinout0516.component;

import org.springframework.stereotype.Component;

@Component
public class WechatMPVariable {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
