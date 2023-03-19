package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;

public class PersonNotValidException extends BaseException {
    public PersonNotValidException(String message) {
        super(
                HttpStatus.NOT_FOUND,
                new ApiError("player.not.valid",
                        message)
        );
    }
}
