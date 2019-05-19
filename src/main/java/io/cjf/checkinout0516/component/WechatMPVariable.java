package io.cjf.checkinout0516.component;

import org.springframework.stereotype.Component;

@Component
public class WechatMPVariable {

    private String accessToken;

    public WechatMPVariable(){
        accessToken = "21_xZqhyr0-J9Vjx_F8B3D5ah3yvgTLiqmrINR-VNCGfk0uELwtY52GGnlrvHmDD8kn_ArZAeWH4W8F_14mUcgXVxYUScPZ4sVFmoHCyILP3Wr1DmG44KOfwh0uA71Vk05B5tv6Ht__BpQEuBVRBYNiADASPE";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
