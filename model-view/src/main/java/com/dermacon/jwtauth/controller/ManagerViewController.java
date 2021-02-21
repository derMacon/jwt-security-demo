package com.dermacon.jwtauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerViewController {

    @GetMapping("/resource")
    public String getManagerResource() {
        return "this is a manager resource";
    }

}
