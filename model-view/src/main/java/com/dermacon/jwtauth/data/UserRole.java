package com.dermacon.jwtauth.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.dermacon.jwtauth.data.AppUserPermission.*;

public enum UserRole {
    ROLE_ANONYMOUS(),
    ROLE_USER(COURSE_READ),
    ROLE_MANAGER(COURSE_READ, COURSE_WRITE),
    ROLE_ADMIN(COURSE_READ, COURSE_WRITE, PERSON_READ, PERSON_WRITE);

    private final Set<AppUserPermission> permissions;

    UserRole(AppUserPermission... permissions) {
        this.permissions = new HashSet<>(Arrays.asList(permissions));
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }
}