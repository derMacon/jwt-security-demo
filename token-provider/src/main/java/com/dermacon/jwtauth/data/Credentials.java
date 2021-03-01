package com.dermacon.jwtauth.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

//@Value
//@Data
//@Builder
//@AllArgsConstructor
public class Credentials {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
