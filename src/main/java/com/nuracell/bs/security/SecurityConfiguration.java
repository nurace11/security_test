package com.nuracell.bs.security;

import com.nuracell.bs.configuration.JWTFilter;
import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.repository.AppUserRepository;
import com.nuracell.bs.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsService appUserDetailsService;

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeHttpRequests()
                .requestMatchers("/", "/api/**", "/api/v1/drones/**", "/auth/**")
                .permitAll()
                .anyRequest()
                .hasAnyRole("ADMIN")

//                .and()
//                .authenticationProvider(authenticationProvider())
//                .httpBasic()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// spring will not save session anymore;

                .and()
                .addFilterAfter(jwtFilter, BasicAuthenticationFilter.class);

//        http.addFilter(jwtFilter);


        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return authentication -> {
            String username = authentication.getName();

            UserDetails appUserDetails = appUserDetailsService.loadUserByUsername(username);


            String password = authentication.getCredentials().toString();

            // compares string with hashed password
            if (!password.equals(appUserDetails.getPassword())) {
                System.out.println(appUserDetails.getPassword());
                throw new BadCredentialsException("Incorrect password");
            }

            return new UsernamePasswordAuthenticationToken(appUserDetails, password, appUserDetails.getAuthorities());
        };
    }
}
