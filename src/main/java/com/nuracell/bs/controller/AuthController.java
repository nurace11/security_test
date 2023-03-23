package com.nuracell.bs.controller;

import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.exception.AppUserNotValidException;
import com.nuracell.bs.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
//@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<AppUser> performRegistration(@RequestBody @Valid AppUser appUser,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            StringBuilder errorMsg = new StringBuilder();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new AppUserNotValidException(errorMsg.toString());
        }
        return ResponseEntity.ok(registrationService.register(appUser));
    }
}
