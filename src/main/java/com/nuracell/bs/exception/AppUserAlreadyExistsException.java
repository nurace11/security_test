package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AppUserAlreadyExistsException extends BaseException{
    public AppUserAlreadyExistsException() {
        super(
                HttpStatus.BAD_REQUEST,
                new ApiError(
                        "appuser.already.exists",
                        "registration failed"
                )
        );
    }
}
