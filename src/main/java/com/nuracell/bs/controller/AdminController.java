package com.nuracell.bs.controller;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public ResponseEntity<String> getMessageForAdmin() {
        System.out.println("[admin page]");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("[admin page logic]");

        return ResponseEntity.ok("Admin page");
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{message}")
    public String getMessageWithPathVariable(@PathVariable("message") String message) {
        return "Only admin " + message;
    }
}
