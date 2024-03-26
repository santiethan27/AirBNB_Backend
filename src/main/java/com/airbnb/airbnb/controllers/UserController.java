/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.controllers;

import com.airbnb.airbnb.requests.UserRequest;
import com.airbnb.airbnb.servicies.UserService;
import java.util.Collections;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute UserRequest request, @RequestPart("photo") MultipartFile photo) {
        try {
            if (request.getFirstName() == null || request.getFirstName().isEmpty()
                    || request.getLastName() == null || request.getLastName().isEmpty()
                    || request.getEmail() == null || request.getEmail().isEmpty()
                    || request.getPassword() == null || request.getPassword().isEmpty()
                    || request.getPhone() == null || request.getPhone().isEmpty()
                    || request.getCountry() == null || request.getCountry().isEmpty()
                    || request.getBirthDate() == null || request.getRol() == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Los datos no son correctos."));
            }

            byte[] photoBytes = photo.getBytes();
            userService.registerUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getPhone(), request.getCountry(), photoBytes, request.getBirthDate(), request.getRol());
            return ResponseEntity.ok("Usuario registrado exitosamente.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El correo ya esta en uso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@ModelAttribute UserRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().isEmpty()
                || request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Falta ingresar el email o la contraseña"));
            }
            userService.loginUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("Ingresado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
}
