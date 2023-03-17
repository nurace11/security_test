package com.nuracell.bs.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DroneNoteFoundException extends RuntimeException {
    Long id;
}
