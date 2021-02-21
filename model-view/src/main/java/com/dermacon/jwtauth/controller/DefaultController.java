package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DefaultController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/refresh-token")
    public void refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials,
                               HttpServletResponse response) throws IOException {
        ResponseEntity<String> tokenResponse = null;
        String url = "/all-cookies";
        try {
            AuthenticationRequest authReq = new AuthenticationRequest();
            authReq.setPassword(credentials.getPassword());
            authReq.setUsername(credentials.getUsername());
            tokenResponse = restTemplate.postForEntity("http://token-provider/create-token",
                    authReq, String.class);

            if (tokenResponse.getStatusCode() == HttpStatus.OK) {
                response.addCookie(new Cookie("jwt-token", tokenResponse.getBody()));
                response.sendRedirect("/all-cookies");
            } else {
                response.sendRedirect("/login?error=invalid");
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response.sendRedirect("/login?error=invalid");
        }

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
