package com.airbnb.airbnb.controllers;

import com.airbnb.airbnb.requests.PropertyRequest;
import com.airbnb.airbnb.servicies.PropertyService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProperty(@ModelAttribute PropertyRequest request, List<MultipartFile> images) throws Exception {

        try {
            if (request.getOwner() == null || request.getOwner().isEmpty()
                    || request.getDescription() == null || request.getDescription().isEmpty()
                    || request.getRating() == null
                    || request.getPostalCode() == null || request.getPostalCode().isEmpty()
                    || request.getPropertyTypes() == null
                    || request.getSize() == null) {

                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Los datos no son correctos."));

            }
            List<byte[]> uploadedFileUrls = new ArrayList<>();
            for (MultipartFile file : images) {
                try {
                    byte[] img = file.getBytes();
                    uploadedFileUrls.add(img);
                } catch (IOException e) {
                    // Manejo de errores de subida
                    e.printStackTrace();
                    // Podrías lanzar una excepción personalizada aquí o simplemente continuar con el siguiente archivo
                }
            }
            propertyService.createProperty(request.getOwner(), uploadedFileUrls, request.getDescription(), request.getSize(), request.getAddress(), request.getRating(), request.getPostalCode(), request.getPropertyTypes());
            return ResponseEntity.ok("Propiedad registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
