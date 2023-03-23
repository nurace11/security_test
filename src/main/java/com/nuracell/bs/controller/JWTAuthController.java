package com.nuracell.bs.controller;

import com.nuracell.bs.dto.AppUserDTO;
import com.nuracell.bs.dto.AuthenticationDTO;
import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.exception.AppUserNotFoundException;
import com.nuracell.bs.exception.AppUserNotValidException;
import com.nuracell.bs.security.jwt.JWTUtil;
import com.nuracell.bs.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JWTAuthController {
    private final RegistrationService registrationService;


    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

//    private final AuthenticationManager authenticationManager; todo: make this work. Manager vs Provider
    private final AuthenticationProvider authenticationProvider;

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody @Valid AuthenticationDTO authDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            StringBuilder errorMsg = new StringBuilder();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new AppUserNotValidException(errorMsg.toString());
        }

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());

        try {
            authenticationProvider.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authDTO.getUsername());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid AppUserDTO appUserDTO,
                                                   BindingResult bindingResult) {
        AppUser appUser = convertToAppUser(appUserDTO);

//        appUserValidator.validate(appUser, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            StringBuilder errorMsg = new StringBuilder();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new AppUserNotValidException(errorMsg.toString());
        }

        registrationService.register(appUser);

        String token = jwtUtil.generateToken(appUser.getUsername());

        return Map.of("jwt-token", token);
    }

    public AppUser convertToAppUser(AppUserDTO appUserDTO) {
        return this.modelMapper.map(appUserDTO, AppUser.class);
    }
}
