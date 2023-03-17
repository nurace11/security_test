package com.nuracell.bs.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    private ResponseEntity<ApiError> handleBaseException(BaseException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatusCode());
    }
}
