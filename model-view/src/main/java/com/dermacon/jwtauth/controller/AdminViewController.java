package com.dermacon.jwtauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminViewController {


    @GetMapping("/resource")
    public String getAdminResouce() {
        return "this is an admin resource";
    }

}
