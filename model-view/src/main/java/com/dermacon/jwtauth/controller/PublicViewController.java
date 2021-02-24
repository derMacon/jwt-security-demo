package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.InputCredentials;
import com.dermacon.jwtauth.data.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/public")
public class PublicViewController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/check")
    public String test() {
        String out = restTemplate.getForObject("http://token-provider/health", String.class);
        System.out.println(" ----------------------> out: " + out);
        return "test";
    }

    @RequestMapping("/login")
    public String newLogin(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("errorMessage", error);
        model.addAttribute("inputCredentials", new InputCredentials());
        return "public/login-view";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        AppUser userTemplate = new AppUser.Builder()
                .role(UserRole.ROLE_USER)
                .build();
        model.addAttribute("inputUser", userTemplate);
        return "public/register";
    }

}
