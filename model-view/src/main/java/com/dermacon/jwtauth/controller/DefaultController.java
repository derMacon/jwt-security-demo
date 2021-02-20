package com.dermacon.jwtauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class DefaultController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/refresh-token")
    public String refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials,
                               HttpServletResponse response) {
        String token = "";
        try {
            token = restTemplate.getForObject("http://token-provider/create-token", String.class);
        } catch (HttpClientErrorException e) {
            return "redirect:/test";
        } catch (HttpServerErrorException e) {
            return "redirect:/login?errorMessage=invalid2";
        }

        response.addCookie(new Cookie("jwt-token", token));
//        response.addCookie(new Cookie("jwt-token", "invalid"));
//        response.addCookie(new Cookie("jwt-token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEiLCJleHAiOjE2NDk2OTE5MTEsImlhdCI6MTYxMzY5MTkxMX0.R1Qd4wnlHBCw3YbVL-dQJRc8CHRFxiNcXp2uJdnBM-Sg8JSEM9ECdSXrp20pYi_hBNXcpBjTVLsT_NFWpwd45g"));
//        response.sendRedirect(request.getContextPath() + "/new-login");
        return "redirect:/all-cookies";
    }

    @PostMapping("/test")
    public String test(){
        return "test2";
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
