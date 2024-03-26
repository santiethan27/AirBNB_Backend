/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.servicies;

import com.airbnb.airbnb.entities.User;
import com.airbnb.airbnb.enums.Rol;
import com.airbnb.airbnb.repositories.UserRepository;
import com.airbnb.airbnb.requests.UserRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registerUser(String first_name, String last_name, String email, String password, String phone, String Country, byte[] photo, Date birthDate, Rol rol) throws Exception {
        try {
            User user = new User();
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setCountry(Country);
            user.setPhoto(photo);
            user.setBirthdate(birthDate);
            user.setRol(rol);
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void loginUser(String email, String password) throws Exception {
        try {
            System.out.println("hola");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateUser(String userId, UserRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (request.getFirstName() != null) {
                user.setFirst_name(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLast_name(request.getLastName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getPassword() != null) {
                user.setPassword(request.getPassword());
            }
            if (request.getPhone() != null) {
                user.setPhone(request.getPhone());
            }
            if (request.getCountry() != null) {
                user.setCountry(request.getCountry());
            }
            if (request.getPhoto() != null) {
                user.setPhoto(request.getPhoto());
            }
            if (request.getBirthDate() != null) {
                user.setBirthdate(request.getBirthDate());
            }
            if (request.getRol() != null) {
                user.setRol(request.getRol());
            }

            userRepository.save(user);

            userRepository.save(user);
        } else {

        }
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
