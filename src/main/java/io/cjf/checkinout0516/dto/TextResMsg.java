package io.cjf.checkinout0516.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class TextResMsg extends WechatMPResMsg{
    private String Content;

    public TextResMsg(String toUserName, String content) {
        super(toUserName, "text");
        Content = content;
    }

    @JacksonXmlProperty(localName = "Content")
    @JacksonXmlCData
    public String getContent() {
        return Content;
    }
}
