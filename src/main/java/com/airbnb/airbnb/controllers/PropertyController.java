/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.controllers;

import com.airbnb.airbn.servicies.PropertyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String register(@RequestParam("owner") String owner,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("description") String description,
            @RequestParam("size") double size,
            @RequestParam("rating") Integer rating,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("propertyType") String propertyType) {
      //  try {
           //serviceProperty.createProperty(owner, images, description, size, rating,
                  //  postalCode, propertyType)
    
       // }
        //catch (Exception e) {
          
       // }

    }

}
