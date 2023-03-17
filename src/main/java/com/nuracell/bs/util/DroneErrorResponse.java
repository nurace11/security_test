package com.nuracell.bs.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DroneErrorResponse {
    private String message;
    private long timestamp;
}
