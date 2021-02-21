package com.dermacon.jwtauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping("/resource")
//    @PreAuthorize("hasAnyAuthority('student:write')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAdminResouce() {
        return "this is an admin resource";
    }

}
