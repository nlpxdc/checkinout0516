package io.cjf.checkinout0516.dto;

import javax.validation.constraints.NotBlank;

public class WechatMPCommonReqMsg extends WechatMPReqMsg {

//    protected Long MsgId;

    @NotBlank
    public Long getMsgId() {
        return this.getLong("MsgId");
    }

}
