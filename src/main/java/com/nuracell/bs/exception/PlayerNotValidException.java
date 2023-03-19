package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;

public class PlayerNotValidException extends BaseException {
    public PlayerNotValidException(String message) {
        super(
                HttpStatus.BAD_REQUEST,
                new ApiError("player.not.valid",
                        message)
        );
    }
}
