package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.Credentials;
import com.dermacon.jwtauth.exception.CredentialsException;
import com.dermacon.jwtauth.response.JWTTokenResponse;
import com.dermacon.jwtauth.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;


    // --------------- token response ---------------

    /**
     * https://attacomsian.com/blog/set-cookie-with-response-entity-in-spring-boot
     * @param credentials credentials from the user
     * @return token for the given user
     */
    @PostMapping("/create-token")
    public ResponseEntity<String> createToken(@RequestBody Credentials credentials) {
        JWTTokenResponse jwtToken = authenticationService.generateJWTToken(credentials);
        return new ResponseEntity<>(jwtToken.getToken(), HttpStatus.OK);
    }

    @PostMapping("/health")
    public String isAlive_post() {
        return "token provider is alive - post";
    }

    @GetMapping("/health")
    public String isAlive_get() {
        return "token provider is alive - get";
    }

    /**
     * Handles exception thrown by service.
     * To centralize exception handling use @ControllerAdvice annotation
     * see: https://howtodoinjava.com/spring-core/spring-exceptionhandler-annotation/
     * @param ex
     * @return
     */ // todo maybe return Exception as payload???
        // https://stackoverflow.com/questions/52183546/not-able-to-return-responseentity-with-exception-details-in-spring
    @ExceptionHandler({EntityNotFoundException.class, CredentialsException.class})
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    // --------------- register ---------------

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser user) {
        log.info("register user: " + user.getUsername());
        authenticationService.registerNewUser(user);
        return null; // todo
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity handleEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
