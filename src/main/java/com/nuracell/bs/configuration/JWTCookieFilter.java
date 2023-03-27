package com.nuracell.bs.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nuracell.bs.security.jwt.JWTUtil;
import com.nuracell.bs.service.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
@RequiredArgsConstructor
public class JWTCookieFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AppUserDetailsService appUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        String jwt = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if(c.getName().equals("jwt-token")) {
                    jwt = c.getValue();
                }
            }
        }


        if(jwt != null && !jwt.isBlank()) {
            try {
                String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                userDetails.getPassword(),
                                userDetails.getAuthorities());

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (JWTVerificationException ex) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token");
            }
        } else {
            response.sendError(/*HttpServletResponse.SC_BAD_REQUEST*/HttpStatus.BAD_REQUEST.value(),
                    "Invalid JWT Token in Authorization Header");
        }

        filterChain.doFilter(request, response);

    }
}
