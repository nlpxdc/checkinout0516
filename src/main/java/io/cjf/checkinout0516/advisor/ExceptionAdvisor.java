package io.cjf.checkinout0516.advisor;

import io.cjf.checkinout0516.dto.TextResMsg;
import io.cjf.checkinout0516.exception.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    public ResponseEntity<TextResMsg> handleClientException(ClientException ex){
        logger.warn("ClientException, Errcode: {}, ErrMsg: {}", ex.getErrCode(), ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        TextResMsg textResMsg = new TextResMsg(ex.getOpenid(), ex.getMessage());
        ResponseEntity<TextResMsg> textResMsgResponseEntity = new ResponseEntity<>(textResMsg, headers, HttpStatus.OK);
        return textResMsgResponseEntity;
    }
}
