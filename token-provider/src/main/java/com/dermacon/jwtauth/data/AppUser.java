package com.dermacon.jwtauth.data;

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

// todo check if lombok works here
@Entity
@Data
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long userId;

    @Column(unique = true)
    private final String username;

    private final String password;

    // todo mail format constraint
    private final String email;

    @Enumerated(EnumType.STRING)
    private final UserRole role;
}
