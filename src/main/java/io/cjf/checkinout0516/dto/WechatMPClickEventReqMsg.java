package io.cjf.checkinout0516.dto;

import javax.validation.constraints.NotBlank;

public class WechatMPClickEventReqMsg extends  WechatMPEventReqMsg{

    //    protected String EventKey;
    @NotBlank
    public String getEventKey() {
        return this.getString("EventKey");
    }
}
