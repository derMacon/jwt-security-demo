package com.dermacon.jwtauth.service;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.InputCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Abstraction layer
 * Although it may seem like the functionality may reside directly in the controller, it may be
 * useful to keep it in a seperate service later on in the project. It's especially useful when
 * implementing a frontend or single page application (e.g. vaadin).
 */
@Service
public class CommunicationService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Provides a new jwt token for the given credentials (but only if the user with those
     * credentials exists in the database)
     * Also sets a cookie with a valid token
     *
     * @param credentials username and password of a given user
     * @param response    response object to which a redirect will be made
     * @throws IOException exception when the redirect to the new url cannot be performed for
     *                     whatever reason
     */
    public void refreshToken(@ModelAttribute(value = "inputCredentials") InputCredentials credentials,
                             HttpServletResponse response) throws IOException {
        String redirect_url = "/public/all-cookies";
        try {

            AppUser user = new AppUser.Builder()
                    .username(credentials.getUsername())
                    .password(credentials.getPassword())
                    .build();

            ResponseEntity<String> tokenResponse = restTemplate
                    .postForEntity("http://token-provider/create-token",
                            user, String.class);

            if (tokenResponse.getStatusCode() == HttpStatus.OK) {
                response.addCookie(new Cookie("jwt-token", tokenResponse.getBody()));
            } else {
                redirect_url = "/login?error=" + tokenResponse.getStatusCode();
            }

            // todo handle java.net.ConnectException (when token-provider is down)
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            redirect_url = "/login?error=404";
        }

        response.sendRedirect(redirect_url);
    }

    public void registerUser(AppUser user, HttpServletResponse response) throws IOException {
        String redirect_url = "/public/all-cookies";
        try {

            ResponseEntity<String> tokenResponse = restTemplate
                    .postForEntity("http://token-provider/register",
                            user, String.class);

            if (tokenResponse.getStatusCode() == HttpStatus.OK) {
                response.addCookie(new Cookie("jwt-token", tokenResponse.getBody()));
            } else {
                redirect_url = "/login?error=" + tokenResponse.getStatusCode();
            }

            // todo handle java.net.ConnectException (when token-provider is down)
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            redirect_url = "/login?error=404";
        }

        response.sendRedirect(redirect_url);
    }

}
