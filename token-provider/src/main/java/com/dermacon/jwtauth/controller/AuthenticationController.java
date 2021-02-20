package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.request.AuthenticationRequest;
import com.dermacon.jwtauth.response.JWTTokenResponse;
import com.dermacon.jwtauth.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * https://attacomsian.com/blog/set-cookie-with-response-entity-in-spring-boot
     * @param request
     * @return
     */
    @PostMapping("/create-token")
    public ResponseEntity createToken(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        JWTTokenResponse jwtToken = authenticationService.generateJWTToken(request.getUsername(),
                request.getPassword());

//        // create a cookie
//        Cookie cookie = new Cookie("token",jwtToken.getToken());
//
//        // expires in 7 days
//        cookie.setMaxAge(7 * 24 * 60 * 60);
//
//        // optional properties
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//
//        // add cookie to response
//        response.addCookie(cookie);

        // TODO: add your login logic here
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @PostMapping("/health")
    public String isAlive_post() {
        return "token provider is alive - post";
    }

    @GetMapping("/health")
    public String isAlive_get() {
        return "token provider is alive - get";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
