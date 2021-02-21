package com.dermacon.jwtauth.repository;

import com.dermacon.jwtauth.data.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findOneByUsername(String userName);
}
