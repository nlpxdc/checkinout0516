package io.cjf.checkinout0516.dto;

import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotBlank;

public class WechatMPReqMsg extends JSONObject {
//    protected String ToUserName;
//    protected String FromUserName;
//    protected Long CreateTime;
//    protected String MsgType;
//    protected Long MsgId;

//    public String getToUserName() {
//        return WechatConstant.MPId;
//    }

    @NotBlank
    public String getFromUserName() {
        return this.getString("FromUserName");
    }

    @NotBlank
    public Integer getCreateTime() {
        return this.getInteger("CreateTime");
    }

    @NotBlank
    public String getMsgType() {
        return this.getString("MsgType");
    }

    @NotBlank
    public Long getMsgId() {
        return this.getLong("MsgId");
    }
}
