package com.dermacon.jwtauth.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Email(message = "Email should be valid")
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;
}
