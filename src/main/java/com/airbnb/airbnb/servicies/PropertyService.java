/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.servicies;

import com.airbnb.airbnb.entities.City;
import com.airbnb.airbnb.entities.Country;
import com.airbnb.airbnb.repositories.RepositoryProperty;
import com.airbnb.airbnb.entities.Property;
import com.airbnb.airbnb.entities.PropertyTypes;
import com.airbnb.airbnb.entities.User;
import com.airbnb.airbnb.enums.PriceTypes;
import com.airbnb.airbnb.enums.States;
import com.airbnb.airbnb.repositories.CityRepository;
import com.airbnb.airbnb.repositories.CountryRepository;
import com.airbnb.airbnb.repositories.PropertyTpRepository;
import com.airbnb.airbnb.repositories.UserRepository;
import com.airbnb.airbnb.requests.PropertyRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Id;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Katerine
 */
@Service
public class PropertyService {

    @Autowired
    private RepositoryProperty repositoryProperty;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PropertyTpRepository propertyTpRepository;

    @Transactional
    public void createProperty(String owner, List<String> images, String description, double size, String address, int rating, String postalCode, String propertyType, String Country, Integer City, String priceType, String title, BigDecimal price) throws Exception {
        try {
            Property property = new Property();
            Optional<User> optionalUser = userRepository.findById(owner);
            if (optionalUser.isPresent()) {
                property.setOwner(optionalUser.get());
            } else {
                throw new IllegalArgumentException("Usuario no encontrado");
            }

            Optional<PropertyTypes> optionalType = propertyTpRepository.findById(propertyType);
            if (optionalType.isPresent()) {
                property.setPropertytypes(optionalType.get());
            } else {
                throw new IllegalArgumentException("Tipo de propiedad no encontrada");
            }

            property.setImages(images);
            property.setTitle(title);
            property.setDescription(description);
            property.setSize(size);
            property.setRating(rating);
            property.setPostalCode(postalCode);
            property.setState(States.ACTIVO);
            property.setPrice(price);
            property.setAdress(address);

            Optional<Country> optionalCountry = countryRepository.findById(Country);
            if (optionalCountry.isPresent()) {
                property.setCountry(optionalCountry.get());
            } else {
                throw new IllegalArgumentException("Pais no encontrado");
            }

            Optional<City> optionalCity = cityRepository.findById(City);
            if (optionalCity.isPresent()) {
                property.setCity(optionalCity.get());
            } else {
                throw new IllegalArgumentException("Ciudad no encontrado");
            }
            PriceTypes types = findPriceType(priceType);
            if (types != null) {
                property.setPriceType(types);
            } else {
                throw new IllegalArgumentException("Tipo de prrecio no valido" + priceType);
            }

            repositoryProperty.save(property);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Transactional
    public void updateProperty(String propertyId, PropertyRequest request) throws Exception {
        System.out.println("Entrando");
        Property property = repositoryProperty.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar la propiedad con el Id " + propertyId));

        if (request.getOwner() != null) {
            Optional<User> optionalUser = userRepository.findById(request.getOwner());
            if (optionalUser.isPresent()) {
                property.setOwner(optionalUser.get());
            } else {
                throw new IllegalArgumentException("Usuario no encontrado");
            }
        }

        if (request.getPropertytypes() != null) {
            Optional<PropertyTypes> optionalType = propertyTpRepository.findById(request.getPropertytypes());
            if (optionalType.isPresent()) {
                property.setPropertytypes(optionalType.get());
            } else {
                throw new IllegalArgumentException("Tipo de propiedad no encontrada");
            }
        }

        if (request.getTitle() != null) {
            property.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            property.setDescription(request.getDescription());
        }
        if (request.getRating() != null) {
            property.setRating(request.getRating());
        }
        if (request.getSize() != null) {
            property.setSize(request.getSize());
        }
        if (request.getPostalCode() != null) {
            property.setPostalCode(request.getPostalCode());
        }
        if (request.getPrice() != null) {
            property.setPrice(request.getPrice());
        }
        if (request.getCountry() != null) {
            Optional<Country> optionalCountry = countryRepository.findById(request.getCountry());
            if (optionalCountry.isPresent()) {
                property.setCountry(optionalCountry.get());
            } else {
                throw new IllegalArgumentException("Ciudad no encontrado");
            }
        }
        if (request.getCity() != null) {
            Optional<City> optionalCity = cityRepository.findById(request.getCity());
            if (optionalCity.isPresent()) {
                property.setCity(optionalCity.get());
            } else {
                throw new IllegalArgumentException("Ciudad no encontrado");
            }
        }
        if (request.getPriceTypes() != null) {
            PriceTypes types = findPriceType(request.getPriceTypes());
            if (types != null) {
                property.setPriceType(types);
            } else {
                throw new IllegalArgumentException("Tipo de precio no valido" + request.getPriceTypes());
            }
        }
        repositoryProperty.save(property);
    }

    public void deleteProperty(String propertyId) throws Exception {
        Property property = repositoryProperty.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("No se puedo encontrar el Id" + propertyId));
        repositoryProperty.delete(property);
    }

    private PriceTypes findPriceType(String priceType) {
        for (PriceTypes types : PriceTypes.values()) {
            if (types.name().equalsIgnoreCase(priceType)) {
                return types;
            }
        }
        return null;
    }

    public List<Property> getAllProperties() {
        return repositoryProperty.findAll();
    }

    public Property getPropertyById(String id) {
        Optional<Property> optionalProperty = repositoryProperty.findById(id);
        return optionalProperty.orElse(null);
    }

}
