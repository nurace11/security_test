package com.nuracell.bs.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    private ResponseEntity<ApiError> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ex.getApiError(), ex.getHttpStatusCode());
    }
}
