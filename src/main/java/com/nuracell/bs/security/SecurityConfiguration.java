package com.nuracell.bs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/", "/api/**")
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasAnyRole("admin")
                .and()
                .httpBasic();

        System.out.println(encodedBasicAuth("user", "5c0d02f4-9d69-4e9f-8dc3-2e67098b51f5"));

        return http.build();
    }

    //
    // HTTP
    // Request headers
    // Authorization: [username:password encoded as Base64]
    // Java code below

    public String encodedBasicAuth(String username, String password) {
        String toEncode = username + ":" + password;
        var encoder = Base64.getEncoder();

        StringBuilder theEncodedString = new StringBuilder();
        byte[] bytes = encoder.encode(toEncode.getBytes());
        for (byte b : bytes) {
            theEncodedString.append((char) b );
        }

        return theEncodedString.toString();
    }
}
