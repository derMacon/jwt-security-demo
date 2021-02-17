package com.dermacon.jwtauth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenService {

    // todo
    private String secret = "asdfasdfasdfasdfasdf";

    // todo
    private Long expiration = new Long(3600000);

//    public JwtTokenService(@Value("${jwt.secret}") String secret,
//                           @Value("${jwt.expiration}") Long expiration) {
//        this.secret = secret;
//        this.expiration = expiration;
//    }

    public String generateToken(String username) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 10000);
    }
}

