package com.nuracell.bs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatusCode;

@Getter
@ToString
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatusCode httpStatusCode;
    private ApiError apiError;
}
