package com.dermacon.jwtauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @RequestMapping("/")
    public String test() {
        return "test\n";
    }

}
