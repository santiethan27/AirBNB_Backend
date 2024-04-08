package com.airbnb.airbnb.controllers;

import com.airbnb.airbnb.requests.UserRequest;
import com.airbnb.airbnb.servicies.EmailService;
import com.airbnb.airbnb.servicies.UserService;
import java.util.Collections;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final UserService userService;
    private final EmailService emailService;

    public EmailController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/registeremail")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        try {
            // Verificar si los datos necesarios para el registro están presentes
            if (request.getFirstName() == null || request.getFirstName().isEmpty()
                    || request.getLastName() == null || request.getLastName().isEmpty()
                    || request.getEmail() == null || request.getEmail().isEmpty()
                    || request.getPassword() == null || request.getPassword().isEmpty()
                    || request.getPhone() == null || request.getPhone().isEmpty()
                    || request.getCountry() == null || request.getCountry().isEmpty()
                    || request.getBirthDate() == null || request.getRol() == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Los datos no son correctos."));
            }

            // Registrar al usuario
            byte[] photoBytes = request.getPhoto().getBytes();
            userService.registerUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), request.getPhone(), request.getCountry(), photoBytes, request.getBirthDate());
            
            // Enviar correo de confirmación
            emailService.sendEmail(request.getEmail(), "Confirmación de cuenta", "¡Gracias por registrarte!");

            return ResponseEntity.ok("Registro exitoso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "El correo ya está en uso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}

