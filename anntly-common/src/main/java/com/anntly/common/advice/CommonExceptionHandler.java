package com.anntly.common.advice;

import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  //拦截Controller
public class CommonExceptionHandler {

    @ExceptionHandler(AnnException.class)
    public ResponseEntity<ExceptionResult> handleException(AnnException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
