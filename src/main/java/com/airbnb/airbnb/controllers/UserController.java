/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.controllers;

import com.airbnb.airbn.requests.UserRequest;
import com.airbnb.airbn.servicies.UserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody UserRequest request) {
        try {
            userService.registerUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getPhone(), request.getCountry(), request.getPhoto(), request.getBirthDate(), request.getRol());
            return ResponseEntity.ok("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
