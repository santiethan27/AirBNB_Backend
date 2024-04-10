//Dev Duque
package com.airbnb.airbnb.controllers;

import com.airbnb.airbnb.requests.PropertyTpRequest;
import com.airbnb.airbnb.servicies.PropertyTpService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-type")
@Controller
public class PropertyTpController {

    @Autowired
    private CloudinaryController cloudinaryController;

    @Autowired
    private PropertyTpService propertyTpService;

      @PostMapping("register-propertytype")
    public ResponseEntity<?> registerBlog(@ModelAttribute PropertyTpRequest request) throws Exception {
            try {
            if ( request.getTitle()== null || request.getTitle().isEmpty()
                    || request.getImages() == null) {
                
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", request));
            }
            String uploadedUrls = cloudinaryController.upload(request.getImages());

            propertyTpService.createPropertyType(request.getTitle(), uploadedUrls);
            return ResponseEntity.ok("Blog  registrado exitosamente.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", request));
        }
    }

}
