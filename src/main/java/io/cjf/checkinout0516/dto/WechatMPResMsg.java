package io.cjf.checkinout0516.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.cjf.checkinout0516.constant.WechatConstant;

import java.util.Date;

@JacksonXmlRootElement(localName = "xml")
public class WechatMPResMsg {
    protected String ToUserName;
    protected String FromUserName;
    protected Long CreateTime;
    protected String MsgType;

    public WechatMPResMsg(String toUserName, String msgType) {
        ToUserName = toUserName;
        CreateTime = new Date().getTime();
        MsgType = msgType;
    }

    @JacksonXmlProperty(localName = "ToUserName")
    @JacksonXmlCData
    public String getToUserName() {
        return ToUserName;
    }

    @JacksonXmlProperty(localName = "FromUserName")
    @JacksonXmlCData
    public String getFromUserName() {
        return WechatConstant.MPId;
    }

    @JacksonXmlProperty(localName = "CreateTime")
    public Long getCreateTime() {
        return CreateTime;
    }

    @JacksonXmlProperty(localName = "MsgType")
    @JacksonXmlCData
    public String getMsgType() {
        return MsgType;
    }
}
