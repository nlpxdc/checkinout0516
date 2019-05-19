package io.cjf.checkinout0516.dto;

import com.alibaba.fastjson.JSONObject;

public class WechatMPReqMsg extends JSONObject {
//    protected String ToUserName;
//    protected String FromUserName;
//    protected Long CreateTime;
//    protected String MsgType;
//    protected Long MsgId;

//    public String getToUserName() {
//        return WechatConstant.MPId;
//    }

    public String getFromUserName() {
        return this.getString("FromUserName");
    }

    public Long getCreateTime() {
        return this.getLong("CreateTime");
    }

    public String getMsgType() {
        return this.getString("MsgType");
    }

    public Long getMsgId() {
        return this.getLong("MsgId");
    }
}
