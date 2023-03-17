package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class PlayerNotFoundException extends BaseException {
    public PlayerNotFoundException(Long id) {
        super(
                HttpStatus.NOT_FOUND,
                new ApiError(
                        "player.not.found",
                        "Player with id: %d not found".formatted(id)
                )
        );
    }
}
