package com.nuracell.bs.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    @Size(min = 3, max = 255)
    private String username;
    private String password;
    private String appRole;
}
