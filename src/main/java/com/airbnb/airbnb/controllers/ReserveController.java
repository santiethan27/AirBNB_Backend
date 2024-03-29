package com.airbnb.airbnb.controllers;

import com.airbnb.airbnb.enums.PropertyTypes;
import com.airbnb.airbnb.requests.ReserveRequest;
import com.airbnb.airbnb.servicies.ReserveService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserve")
@Controller
public class ReserveController {
    
    @Autowired
    private ReserveService reserveService;
    
    @PostMapping("/createreserve")
    public ResponseEntity<?> registerReserve(@ModelAttribute ReserveRequest request) throws Exception{
        try{
            if(request.getClient() == null || request.getClient().isEmpty()
                    || request.getProperty() == null || request.getProperty().isEmpty()
                    || request.getDetail() == null || request.getDetail().isEmpty()
                    || request.getTotal_quatity() != 0
                    || request.getState() == null || request.getState() == null
                    || request.getDate_reserve() == null || request.getDate_reserve() == null){
                
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", request));
            }
            
            reserveService.createReserve(request.getClient(),request.getProperty(),request.getDetail(),request.getTotal_quatity(),request.getDate_reserve(),request.getState());
            return ResponseEntity.ok("Reserva registrado exitosamente.");
         } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", request));
        }
    }
    
    @PutMapping("/updatereserve/{id}")
    public ResponseEntity<?> updateReserve(@PathVariable String id, @ModelAttribute ReserveRequest request){
        try{
           reserveService.updateReserve(id, request);
           return ResponseEntity.ok("Reserva actualizada con exito");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserve (@PathVariable String id){
        try{
           reserveService.deleteReserve(id);
           return ResponseEntity.ok("Reserva elminada con exito");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}