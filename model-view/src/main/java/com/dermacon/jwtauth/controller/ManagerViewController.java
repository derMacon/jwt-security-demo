package com.dermacon.jwtauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerViewController {

    @GetMapping("/resource")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public String getManagerResource() {
        return "this is a manager resource";
    }

}
