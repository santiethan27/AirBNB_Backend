/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbn.servicies;

import com.airbnb.airbn.repositories.RepositoryProperty;
import com.airbnb.airbnb.entities.Property;
import com.airbnb.airbnb.enums.PropertyTypes;
import com.airbnb.airbnb.enums.States;
import java.util.List;
import java.util.Optional;
import javax.persistence.Id;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Katerine
 */
@Service
public class PropertyService {

    @Autowired
    private RepositoryProperty repositoryProperty;

    @Transactional
    public void createProperty(String owner, List<byte[]> images, String description, double size, String address, int rating, String postalCode, String propertyType) throws Exception {

        Property property = new Property();
        property.setOwner(owner);
        property.setImages(images);
        property.setDescription(description);
        property.setSize(size);
        property.setRating(rating);
        property.setPostalCode(postalCode);
        property.setState(States.ACTIVO);
        PropertyTypes type = findPropertyType(propertyType);
        if (type != null) {
            property.setPropertyTypes(type);

        } else {
            //  this.deleteAddress
            throw new IllegalArgumentException("Tipo de propiedad no valido" + propertyType);
        }
        property.setAdress(address);
        repositoryProperty.save(property);

    }

    @Transactional
    public void updateProperty(String propertyId, String owner, List<byte[]> images, String description, double size, String address, Integer rating, String postalCode, String propertyType) throws Exception {
        System.out.println("Entrando");
        Property property = repositoryProperty.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar la propiedad con el Id " + propertyId));
        if (owner != null) {
            property.setOwner(owner);
        }
        if (description != null) {
            property.setDescription(description);
        }
        if (rating != null) {
            property.setRating(rating);
        }
        if (postalCode != null) {
            property.setPostalCode(postalCode);

        }
        if (propertyType != null) {
            PropertyTypes type = findPropertyType(propertyType);
            if (type != null) {
                property.setPropertyTypes(type);
            } else {
                throw new IllegalArgumentException("Tipo de propiedad no valido" + propertyType);
            }
        }
        repositoryProperty.save(property);
    }

    public void deleteProperty(String propertyId) throws Exception {
        Property property = repositoryProperty.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("No se puedo encontrar el Id" + propertyId));
        repositoryProperty.delete(property);
    }

    private PropertyTypes findPropertyType(String propertyType) {
        for (PropertyTypes type : PropertyTypes.values()) {
            if (type.name().equalsIgnoreCase(propertyType)) {
                return type;
            }
        }
        return null;
    }

    @Transactional
    public List<Property> getAllProperties() {
        return repositoryProperty.findAll();
    }
   public Optional <Property> getPropertyById (String id){
     return repositoryProperty.findById(id);
   } 
   
}
