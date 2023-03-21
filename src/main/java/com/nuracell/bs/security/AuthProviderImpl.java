package com.nuracell.bs.security;

import com.nuracell.bs.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Deprecated // this class is needed if we have specific authentication or if authentication made on CAS
//@Component
@RequiredArgsConstructor
public class AuthProviderImpl implements AuthenticationProvider {
    private final AppUserDetailsService appUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails appUserDetails = appUserDetailsService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();

        if (!password.equals(appUserDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        return new UsernamePasswordAuthenticationToken(appUserDetails, password, appUserDetails.getAuthorities() );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
