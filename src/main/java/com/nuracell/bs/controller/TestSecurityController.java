package com.nuracell.bs.controller;

import com.nuracell.bs.exception.PlayerNotFoundException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/security")
//@Secured("ROLE_ADMIN")
//@PreAuthorize("hasRole('ADMIN')")
//@RolesAllowed("ROLE_ADMIN")
public class TestSecurityController {

    @GetMapping("/")
    public ResponseEntity<String> greet() {

        System.out.println("Greetings");
        return ResponseEntity.ok("TestSecurityController");
    }

//    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @Secured("ROLE_ADMIN")
    @GetMapping("/secured")
    public ResponseEntity<String> greetAdmin() {

        System.out.println("Secured");
        return ResponseEntity.ok("Secured admin");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/pre-auth")
    public ResponseEntity<String> preAuthGreetAdmin() {

        System.out.println("PreAuth");
        return ResponseEntity.ok("PreAuthorize admin");
    }

//    @PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
//    @PostAuthorize("#username == authentication.principal.username")
    @PostAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/post-auth")
    public ResponseEntity<String> postAuthGreetAdmin() {

        System.out.println("PostAuth");
        return ResponseEntity.ok("PostAuthorize admin");
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping("/roles-allowed")
    public ResponseEntity<String> rolesAllowed() {

        System.out.println("Roles allowed");
        return ResponseEntity.ok("Roles Allowed");
    }

    // filter a collection argument before executing the method:
    @Deprecated
    @PreFilter("filterObject != authentication.principal.username")
    @GetMapping("/pre-filter")
    public ResponseEntity<String> preFilter(List<String> usernames) {

        System.out.println("PreFilter");
        System.out.println(String.join(" ", usernames));
        return ResponseEntity.ok("@PreFilter");
    }

    @Deprecated
    @PreFilter(value = "filterObject != authentication.principal.username",
               filterTarget = "usernames")
    @GetMapping("/pre-filter2")
    public String joinUsernamesAndRoles(List<String> usernames,
                                        List<String> roles) {

        System.out.println("PreFilter");
        return usernames.stream().collect(Collectors.joining(";"))
                + ":" + roles.stream().collect(Collectors.joining(";"));
    }

    @Deprecated
    // the name filterObject refers to the current object in the returned collection.
    @PostFilter("filterObject == authentication.principal.username")
    @GetMapping("/post-filter")
    public List<String> getAllUsernamesExceptCurrent() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new PlayerNotFoundException(1L);
        }

        var list = new ArrayList<String>(){{
            add("user");
            add(auth.getName());
            add("admon");
        }};

        System.out.println(list);

        return list;
    }
}
