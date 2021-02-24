package com.dermacon.jwtauth.service;

import com.dermacon.jwtauth.data.Credentials;
import com.dermacon.jwtauth.exception.CredentialsException;
import com.dermacon.jwtauth.repository.AccountRepository;
import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.response.JWTTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Generates a token for a given credentials object. Serves as a wrapper for the method with
     * the same name taking in a AppUser instance. The AppUser consists out of the username,
     * password, role and email. This Credentials input only consists out of a username and
     * password and is generally only used when the given user already exists and only needs to
     * refresh its token
     * @param credentials simplified user instance (see description above)
     * @return jwt-token generated for the user in the database
     */
    public JWTTokenResponse generateJWTToken(Credentials credentials) {
        // todo checkout infos about optionals
        Optional<AppUser> user = accountRepository.findOneByUsername(credentials.getUsername());
        if (user.isPresent()) {
            return generateJWTToken(user.get());
        } else {
            throw new EntityNotFoundException("Account not found");
        }
    }

    /**
     * Generates a jwt token for a given AppUser instance
     * @param inputUser user for which the new token should be created
     * @return jwt token for the user
     */
    public JWTTokenResponse generateJWTToken(AppUser inputUser) {
        Optional<AppUser> db_user =  accountRepository.findOneByUsername(inputUser.getUsername());
        if (db_user.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }
        if (!db_user.get().equals(inputUser)) {
            throw new CredentialsException("invalid credentials");
        }
        return new JWTTokenResponse(jwtTokenService.generateToken(inputUser));
    }

    public void registerNewUser(AppUser user) {
        if (accountRepository.existsByEmail(user.getEmail())) {
            throw new EntityExistsException("email already exists: " + user.getEmail());
        }
        if (accountRepository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException("username already exists: " + user.getUsername());
        }

        accountRepository.save(user);
    }

}
