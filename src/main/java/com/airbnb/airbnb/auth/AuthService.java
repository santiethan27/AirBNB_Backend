/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.auth;

import com.airbnb.airbnb.auth.AuthResponse;
import com.example.FirtsTest.entities.User;
import com.example.FirtsTest.enums.Role;
import com.airbnb.airbnb.jwt.JwtService;
import org.springframework.stereotype.Service;
import com.example.FirtsTest.repositories.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Usuario
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RepositoryUser userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserDetails userDetails = userRepository.findByEmail(request.getEmail()).orElseThrow();
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtService.getToken(userDetails);
            return AuthResponse.builder()
                    .token(token)
                    .user(user)
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Email or password is incorrect");
        }
    }

    public AuthResponse register(RegisterRequest request) {

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRol(Role.USER);
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());
        user.setPhone(request.getPhone());
        user.setBirthdate(request.getBirthdate());

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .user(user)
                .build();

    }

    public AuthResponse verify(String token) {
        String userName = jwtService.getUsernameFromToken(token);
        User user = userRepository.findByEmail(userName).orElseThrow();
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .user(user)
                .build();
    }

}
