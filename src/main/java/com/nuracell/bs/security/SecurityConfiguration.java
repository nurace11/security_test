package com.nuracell.bs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeHttpRequests()
                .requestMatchers("/", "/api/**", "/api/v1/drones/**")
                .permitAll()
                .anyRequest() // any other request must be authenticated (optional)
                .hasAnyRole("ADMIN")

//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .httpBasic();

        System.out.println("encoded basic auth: " + encodedBasicAuth("user", "5c0d02f4-9d69-4e9f-8dc3-2e67098b51f5"));
        System.out.println("authProvider: " + authenticationProvider());
        System.out.println("authProvider: ");
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        System.out.println("authenticationProvider");
        return daoAuthenticationProvider;
    }

    // InMemoryDataBase
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails roxana = User.builder()
                .username("roxana")
                .password(passwordEncoder.encode("roxana"))
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(admin, roxana);
        return inMemoryUserDetailsManager;
    }

    // HTTP
    // Request headers
    // Authorization: [username:password encoded as Base64]
    public String encodedBasicAuth(String username, String password) {
        var encoder = Base64.getEncoder();
        byte[] bytes = encoder.encode((username + ":" + password).getBytes());
        return new String(bytes);
    }


}
