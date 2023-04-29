package com.nuracell.bs.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestCatchFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getClass().getName());
        System.out.println("request.getRemoteUser() = " + request.getRemoteUser());
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
        System.out.println("request.getRemotePort() = " + request.getRemotePort() + "\n");
        System.out.println();

        System.out.println(response.getClass().getName());
        System.out.println("response.getStatus() = " + response.getStatus());
        System.out.println("-------------------------------------------");

        filterChain.doFilter(request, response);
    }
}
