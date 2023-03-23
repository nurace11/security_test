package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AppUserNotValidException extends BaseException{
    public AppUserNotValidException(String message) {
        super(
                HttpStatus.BAD_REQUEST,
                new ApiError(
                        "appuser.not.found",
                        message
                )
        );
    }
}
