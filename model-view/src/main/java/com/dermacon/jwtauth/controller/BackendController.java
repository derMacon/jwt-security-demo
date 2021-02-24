package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.InputCredentials;
import com.dermacon.jwtauth.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private CommunicationService communicationService;


    /**
     * Provides a new jwt token for the given credentials (but only if the user with those
     * credentials exists in the database)
     * Also sets a cookie with a valid token
     * @param credentials username and password of a given user
     * @param response response object to which a redirect will be made
     * @throws IOException exception when the redirect to the new url cannot be performed for
     * whatever reason
     */
    @PostMapping("/refresh-token")
    public void refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials,
                               HttpServletResponse response) throws IOException {
        communicationService.refreshToken(credentials, response);
    }


    @PostMapping("/register")
    public void createUser(@ModelAttribute(value = "inputUser")AppUser user,
        HttpServletResponse response) throws IOException {
            communicationService.registerUser(user, response);
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
