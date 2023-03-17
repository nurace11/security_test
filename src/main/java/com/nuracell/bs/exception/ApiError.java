package com.nuracell.bs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ApiError {
    private String errorCode;
    private String description;
}
