package io.cjf.checkinout0516.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WebClientException extends Exception{
    public WebClientException(String msg){
        super(msg);
    }
}
