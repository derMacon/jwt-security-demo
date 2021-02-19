package com.dermacon.jwtauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class DefaultController {

    @PostMapping("/refresh-token")
    public String refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials,
                               HttpServletResponse response) {
//        response.addCookie(new Cookie("jwt-token", "invalid"));
        response.addCookie(new Cookie("jwt-token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEiLCJleHAiOjE2NDk2OTE5MTEsImlhdCI6MTYxMzY5MTkxMX0.R1Qd4wnlHBCw3YbVL-dQJRc8CHRFxiNcXp2uJdnBM-Sg8JSEM9ECdSXrp20pYi_hBNXcpBjTVLsT_NFWpwd45g"));
        return credentials.toString();
    }

    @GetMapping("/all-cookies")
    public String readAllCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }

        return "No cookies";
    }
}
