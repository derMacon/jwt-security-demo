package com.dermacon.jwtauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;

@Service
public class TokenService {

    @Autowired
    private RestTemplate restTemplate;

//    public void refreshToken() {
//
//        String token = restTemplate.getForObject("http://token-provider/create-token",
//                String.class);
////        response.addCookie(new Cookie("jwt-token", "invalid"));
////        response.addCookie(new Cookie("jwt-token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjEiLCJleHAiOjE2NDk2OTE5MTEsImlhdCI6MTYxMzY5MTkxMX0.R1Qd4wnlHBCw3YbVL-dQJRc8CHRFxiNcXp2uJdnBM-Sg8JSEM9ECdSXrp20pYi_hBNXcpBjTVLsT_NFWpwd45g"));
//        response.addCookie(new Cookie("jwt-token", token));
//    }


}
