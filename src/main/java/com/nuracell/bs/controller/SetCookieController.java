package com.nuracell.bs.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/cookie")
public class SetCookieController {
    @GetMapping("/set")
    public String setCookie(HttpServletResponse response) {
        Cookie authCookie = new Cookie("spring-cookie", "abfbafbaf3243.abcdef132.19478d27F62cA");
        response.addCookie(authCookie);

        return response.toString();
    }

    @GetMapping("/set/{key}/{value}")
    public String setCustomCookie(HttpServletResponse response,
                                  @PathVariable("key") String cookieName,
                                  @PathVariable("value") String cookieValue) {
        response.addCookie(new Cookie(cookieName, cookieValue));
        return response.toString();
    }

    @GetMapping("/show")
    String showCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for(Cookie c : cookies) {
                stringBuilder.append(c.getName()).append(":").append(c.getValue()).append("\n");
            }

            return stringBuilder.toString();
        }

        return "no cookies";
    }

    @GetMapping("/clear")
    public String clearCookies(HttpServletResponse response,
                               HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        return Arrays.toString(request.getCookies());
    }

    @GetMapping("/attack")
    public String cookieAttack(HttpServletResponse response,
                               HttpServletRequest request) {
        for (int i = 0; i < 5; i++) {
            response.addCookie(new Cookie(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        }

        return Arrays.toString(request.getCookies());
    }
}
