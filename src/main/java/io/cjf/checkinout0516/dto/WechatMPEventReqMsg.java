package io.cjf.checkinout0516.dto;

import javax.validation.constraints.NotBlank;

public class WechatMPEventReqMsg extends WechatMPReqMsg{

//    protected String Event;

    @NotBlank
    public String getEvent() {
        return this.getString("Event");
    }
}
