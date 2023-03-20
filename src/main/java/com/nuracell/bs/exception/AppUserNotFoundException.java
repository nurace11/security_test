package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class AppUserNotFoundException extends BaseException {
    public AppUserNotFoundException(String username) {
        super(
                HttpStatus.NOT_FOUND,
                new ApiError("appuser.not.found",
                        "AppUser with username %s not found".formatted(username))
        );
    }
}
