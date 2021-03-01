package com.dermacon.jwtauth.utils;

import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.data.UserRole;

public class AppUserUtils {

    public static AppUser createRandomUser() {
        return AppUser.builder()
                .email("test@mail.com")
                .username("admin1")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();
    }

    public static AppUser createInvalidUser_emailFormat() {
        return AppUser.builder()
                .email("invalidEmail.com")
                .username("admin1")
                .password("password")
                .role(UserRole.ROLE_ADMIN)
                .build();
    }

}
