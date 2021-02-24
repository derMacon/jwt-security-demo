package com.dermacon.jwtauth.data;

public enum AppUserPermission {
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    PERSON_READ("person:read"),
    PERSON_WRITE("person:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}