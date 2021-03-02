package com.dermacon.jwtauth.utils;

import com.dermacon.jwtauth.data.Credentials;

public class CredentialsUtils {

    public static Credentials createRandomCredentials() {
//        return Credentials.builder()
//                .username("testUser")
//                .password("password")
//                .build();
        return null;
    }

    public static Credentials createInvalidCredentials_usernameNull() {
//        return Credentials.builder()
//                .password("password")
//                .build();
        return new Credentials("username", "pw");
    }
}

