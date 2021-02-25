package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.Credentials;
import com.dermacon.jwtauth.data.UserRole;
import com.dermacon.jwtauth.repository.AccountRepository;
import com.dermacon.jwtauth.service.AuthenticationService;
import com.dermacon.jwtauth.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
//    @Before
//    public void beforeTest() {
//        MockMvc mockMvc = standaloneSetup(authenticationController)
//                .setControllerAdvice(new RestExceptionHandler())
//                .build();
//    }

    @Test
    public void test_validUser_returns200() throws Exception {
        when(authenticationService.generateJWTToken(Mockito.any(Credentials.class)))
                .thenThrow(new EntityNotFoundException("Account not found"));

        AppUser user = AppUser.builder()
                .email("test@mail.com")
                .username("admin1")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();

        mockMvc.perform(post("/create-token")
                .contentType("application/json").content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound()); // 404;
    }





    @Test
    public void test_invalidUser_returns404() throws Exception {
//        mockMvc = standaloneSetup()
//                .setControllerAdvice(new YourControllerAdvice())
//                .build();
    }
}