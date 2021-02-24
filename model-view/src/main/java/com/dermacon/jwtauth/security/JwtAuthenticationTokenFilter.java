package com.dermacon.jwtauth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Provide spring security context with authentication provided by the token
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

//    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    // todo delete this
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        final String requestHeader = request.getHeader(this.tokenHeader);
//
//        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
//            String  authToken = requestHeader.substring(7);
//            JwtAuthentication authentication = new JwtAuthentication(authToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; token == null && i < cookies.length; i++) {
            // todo put cookie value constant in application.properties
            if (cookies[i].getName().equals("jwt-token")) {
                token = cookies[i].getValue();
            }
        }

        if (token != null) {
            JwtAuthentication authentication = new JwtAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}

