package com.dermacon.jwtauth.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Unauthorized requests will get a this failure response
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

//        RequestDispatcher dispatcher = getServletContext()
//                .getRequestDispatcher("/forwarded");
//        dispatcher.forward(request, response);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}