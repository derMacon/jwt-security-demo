package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.InputCredentials;
import com.dermacon.jwtauth.request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping(value = "/public/", method = RequestMethod.POST)
public class BackendController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/refresh-token")
    public void refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials,
                               HttpServletResponse response) throws IOException {

        ResponseEntity<String> tokenResponse;
        String redirect_url = "/public/all-cookies";

        try {

            AppUser user = new AppUser.Builder()
                    .username(credentials.getUsername())
                    .password(credentials.getPassword())
                    .build();

            tokenResponse = restTemplate.postForEntity("http://token-provider/create-token",
                    user, String.class);

            if (tokenResponse.getStatusCode() == HttpStatus.OK) {
                response.addCookie(new Cookie("jwt-token", tokenResponse.getBody()));
            } else {
                redirect_url = "/login?error=" + tokenResponse.getStatusCode();
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            redirect_url = "/login?error=404";
        }

        response.sendRedirect(redirect_url);
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
