package com.nuracell.bs.controller;

import com.nuracell.bs.dto.AppUserDTO;
import com.nuracell.bs.dto.AuthenticationDTO;
import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.exception.AppUserNotValidException;
import com.nuracell.bs.security.jwt.JWTUtil;
import com.nuracell.bs.service.RegistrationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
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
@RequestMapping("/cookie/auth")
@RequiredArgsConstructor
public class JWTCookieAuthController {
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authDTO,
                                            HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authDTO.getUsername());

        response.addCookie(generateCookie(token));
        return Map.of("answer", "successful logged in. JWT token saved as cookie with name: 'jwt-token'. value: "
                + token);
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody AppUserDTO appUserDTO,
                                                   HttpServletResponse response) {
        AppUser appUser = convertToAppUser(appUserDTO);
        registrationService.register(appUser);
        String token = jwtUtil.generateToken(appUser.getUsername());

        response.addCookie(generateCookie(token));

        return Map.of("answer", "successful logged in. JWT token saved as cookie with name: 'jwt-token'. value: "
                + token);
    }

    private Cookie generateCookie(String token) {
        var cookie = new Cookie("jwt-token", token);
        cookie.setHttpOnly(true); // for security
        cookie.setMaxAge(3600); // 1 hour in seconds
        return cookie;
    }

    public AppUser convertToAppUser(AppUserDTO appUserDTO) {
        return this.modelMapper.map(appUserDTO, AppUser.class);
    }
}
