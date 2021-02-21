package com.dermacon.jwtauth.service;

import com.dermacon.jwtauth.repository.AccountRepository;
import com.dermacon.jwtauth.data.AppUser;
import com.dermacon.jwtauth.response.JWTTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public JWTTokenResponse generateJWTToken(AppUser user) {
        return accountRepository.findOneByUsername(user.getUsername())
                .filter(account ->
                        passwordEncoder.matches(user.getPassword(),account.getPassword()))
                .map(account -> new JWTTokenResponse(jwtTokenService.generateToken(user.getUsername())))
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
