package com.dermacon.jwtauth.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    @RequestMapping("/")
    public String test() {
        return "test\n";
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
