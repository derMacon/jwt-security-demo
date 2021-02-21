package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.request.AuthenticationRequest;
import com.dermacon.jwtauth.response.JWTTokenResponse;
import com.dermacon.jwtauth.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthenticationRequest request) {
        return null; // todo
    }

    /**
     * https://attacomsian.com/blog/set-cookie-with-response-entity-in-spring-boot
     * @param request
     * @return
     */
    @PostMapping("/create-token")
    public ResponseEntity<String> createToken(@RequestBody AuthenticationRequest request) {
        JWTTokenResponse jwtToken = authenticationService.generateJWTToken(request.getUsername(),
                request.getPassword());
        log.info("entity found");
        return new ResponseEntity<>(jwtToken.getToken(), HttpStatus.OK);
    }

    /**
     * To centralize exception handling use @ControllerAdvice annotation
     * see: https://howtodoinjava.com/spring-core/spring-exceptionhandler-annotation/
     * @return conflict response entity
     */
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<String> handleTokenException() {
//        log.error("entity not found");
//        return new ResponseEntity<>("invalid-token-info", HttpStatus.CONFLICT);
//    }



    /**
     * https://attacomsian.com/blog/set-cookie-with-response-entity-in-spring-boot
     * @param request
     * @return
     */
    @PostMapping("/create-token-old")
    public ResponseEntity createTokenold(@RequestBody AuthenticationRequest request,
                                       HttpServletResponse response) {
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
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
