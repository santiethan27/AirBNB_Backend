/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.servicies;

import com.airbnb.airbnb.entities.User;
import com.airbnb.airbnb.enums.Rol;
import com.airbnb.airbnb.repositories.UserRepository;
import java.util.Date;
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
    public void loginUser(String email, String password) throws Exception{
        try{
            System.out.println("hola");
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}