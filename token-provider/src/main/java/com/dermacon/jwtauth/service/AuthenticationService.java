package com.dermacon.jwtauth.service;

import com.dermacon.jwtauth.data.AccountRepository;
import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.response.JWTTokenResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AuthenticationService {

    private AccountRepository accountRepository;
    private JwtTokenService jwtTokenService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(AccountRepository accountRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTokenResponse generateJWTToken(String username, String password) {
        return accountRepository.findOneByUsername(username)
                .filter(account ->  passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(jwtTokenService.generateToken(username)))
                .orElseThrow(() ->  new EntityNotFoundException("Account not found"));
    }

    public void registerNewUser(AppUser user) {
        if (!accountRepository.findOneByUsername(user.getUsername()).isPresent()) {
            accountRepository.save(user);
        } else {
            throw new EntityNotFoundException("user already exists: " + user);
        }
    }
}
