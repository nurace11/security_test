package com.nuracell.bs.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Hidden
@RestController
@RequestMapping("/api/v1/confidential_information")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class HiddenBalanceController {

    @GetMapping("/{personId}")
    public BigDecimal getPersonBalance(@PathVariable("personId") Long personId) {
        return new BigDecimal("3.00E6");
    }
}
