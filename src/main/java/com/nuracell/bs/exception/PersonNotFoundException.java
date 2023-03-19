package com.nuracell.bs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class PersonNotFoundException extends BaseException {
    public PersonNotFoundException(Long id) {
        super(
                HttpStatus.NOT_FOUND,
                new ApiError("person.not.found",
                        "Person with id %d not found".formatted(id))
        );
    }
}
