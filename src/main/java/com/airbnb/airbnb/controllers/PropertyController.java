package com.airbnb.airbnb.controllers;

import com.airbnb.airbnb.entities.Property;
import com.airbnb.airbnb.requests.PropertyRequest;
import com.airbnb.airbnb.servicies.PropertyService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> registerProperty(@ModelAttribute PropertyRequest request) throws Exception {

        try {
            if (request.getOwner() == null || request.getOwner().isEmpty()
                    || request.getDescription() == null || request.getDescription().isEmpty()
                    || request.getRating() == null
                    || request.getPostalCode() == null || request.getPostalCode().isEmpty()
                    || request.getPropertyTypes() == null
                    || request.getSize() == null || request.getImages() == null) {

                return ResponseEntity.badRequest().body(Collections.singletonMap("error", request));

            }
            System.out.println();
            List<byte[]> uploadedFileUrls = new ArrayList<>();
            for (MultipartFile file : request.getImages()) {
                try {
                    uploadedFileUrls.add(file.getBytes());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar las imágenes.");
                }
            }

            propertyService.createProperty(request.getOwner(), uploadedFileUrls, request.getDescription(), request.getSize(), request.getAddress(), request.getRating(), request.getPostalCode(), request.getPropertyTypes());
            return ResponseEntity.ok("Propiedad registrada exitosamente.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", request));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable String id,@ModelAttribute PropertyRequest request)throws Exception{
         try{
             propertyService.updateProperty(id, request);
             return ResponseEntity.ok("Propiedad actualizada con exito");
         }catch (Exception e){
             return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
         }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable String id) throws Exception{
        try{
            propertyService.deleteProperty(id);
            return ResponseEntity.ok("Propiedad eliminada con exito");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    /*@GetMapping("/get-all")
    public ResponseEntity<List<Property>> getAllUsers() {
        List<Property> properties = propertyService.getAllUsers();
        return ResponseEntity.ok(properties);
    }*/
    //@GetMapping("/{id}")
}
