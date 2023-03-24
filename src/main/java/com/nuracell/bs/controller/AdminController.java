package com.nuracell.bs.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String getMessageForAdmin() {
        return "Admin page";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{message}")
    public String getMessageWithPathVariable(@PathVariable("message") String message) {
        return "Only admin " + message;
    }
}
