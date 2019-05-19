package io.cjf.checkinout0516.component;

import org.springframework.stereotype.Component;

@Component
public class WechatMPVariable {

    private String accessToken;

    public WechatMPVariable(){
        accessToken = "21_spHoen4HWcRmYcpWAI00_R1wqKl4JGmT21yrKWA5S_-jwjb-cdITlNjnUTYdG3CXnxbw3vAIRDrbFDKrUw7JxaRhUhVn5Q_VTUBDfGIO3kQPFABEb7Qj6wY4QY_E1aTlVdztvKdmK1xzZE5qTKOdAEAFWA";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
