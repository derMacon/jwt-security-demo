package com.dermacon.jwtauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {

    @RequestMapping("/")
    public String test() {
        return "test";
    }

    @RequestMapping("/new-login")
    public String newLogin(Model model) {
        model.addAttribute("inputCredentials", new InputCredentials());
        return "login-view";
    }

    @RequestMapping("/test")
    public String testView() {
        return "test";
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials) {
        System.out.println(credentials.toString());
        return "redirect:/";
    }

    @RequestMapping("/cookie")
    public String testCookie(HttpServletRequest req,  HttpServletResponse response) {
        // create a cookie
        Cookie cookie = new Cookie("test_cookie", "testtest");

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // add cookie to response
        response.addCookie(cookie);
        return "works";
    }

}
