package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.Credentials;
import com.dermacon.jwtauth.data.UserRole;
import com.dermacon.jwtauth.exception.CredentialsException;
import com.dermacon.jwtauth.repository.AccountRepository;
import com.dermacon.jwtauth.service.AuthenticationService;
import com.dermacon.jwtauth.service.JwtTokenService;
import com.dermacon.jwtauth.utils.AppUserUtils;
import com.dermacon.jwtauth.utils.CredentialsUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * test cases following tutorial:
 * https://reflectoring.io/spring-boot-web-controller-test/
 */
@WebMvcTest(controllers = AuthenticationController.class)
class RestExceptionHandlerTest {

    /**
     * Used to serialize test data
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;


    /**
     * testing controller advice:
     * https://stackoverflow.com/questions/15302243/spring-mvc-controllers-unit-test-not-calling-controlleradvice
     * @throws Exception
     */
    // todo for some reason this isn't even necessary???
//    @Before
//    public void beforeTest() {
//        MockMvc mockMvc = standaloneSetup(authenticationController)
//                .setControllerAdvice(new RestExceptionHandler())
//                .build();
//    }

    // ----------------- 1) Verifying HTTP Request Matching ----------------- //

    @Test
    public void  test_nonExistentUser_returns404() throws Exception {
        when(authenticationService.generateJWTToken(Mockito.any(Credentials.class)))
                .thenThrow(new EntityNotFoundException("Account not found"));

        Credentials cred = CredentialsUtils.createRandomCredentials();

        mockMvc.perform(post("/create-token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cred)))
                .andExpect(status().isNotFound()); // 404;
    }


    // ----------------- 2) Verifying Input Serialization ----------------- //

    @Test
    public void test_validUsernameInvalidCredentials_returns401() throws Exception {
        when(authenticationService.generateJWTToken(Mockito.any(Credentials.class)))
                .thenThrow(new BadCredentialsException("invalid credentials"));

        Credentials cred = CredentialsUtils.createRandomCredentials();

        mockMvc.perform(post("/create-token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cred)))
                .andExpect(status().isUnauthorized()); // 401
    }


    // ----------------- 3) Verifying Input Validation ----------------- //

    @Test
    public void test_usernameNull_returns400() throws Exception {
        when(authenticationService.generateJWTToken(Mockito.any(Credentials.class)))
                .thenThrow(new BadCredentialsException("invalid credentials"));

        Credentials cred = CredentialsUtils.createInvalidCredentials_usernameNull();

        mockMvc.perform(post("/create-token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cred)))
                .andExpect(status().isBadRequest()); // 400 - bad request

        System.out.println("after");
    }
}
