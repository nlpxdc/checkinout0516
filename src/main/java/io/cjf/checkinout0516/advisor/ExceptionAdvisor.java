package io.cjf.checkinout0516.advisor;

import io.cjf.checkinout0516.exception.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public void handleClientException(ClientException ex){
        logger.warn("ClientException, Errcode: {}, ErrMsg: {}", ex.getErrCode(), ex.getMessage());
    }
}
