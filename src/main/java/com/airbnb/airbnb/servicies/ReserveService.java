package com.airbnb.airbnb.servicies;

import com.airbnb.airbnb.entities.Property;
import com.airbnb.airbnb.entities.Reserve;
import com.airbnb.airbnb.entities.User;
import com.airbnb.airbnb.enums.ReservaType;
import com.airbnb.airbnb.repositories.RepositoryProperty;
import com.airbnb.airbnb.repositories.ReserveRepository;
import com.airbnb.airbnb.repositories.UserRepository;
import com.airbnb.airbnb.requests.ReserveRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveService {
    
    @Autowired
    private RepositoryProperty repositoryProperty;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReserveRepository reserveRepository;
    
    @Transactional
    public void createReserve(String client, String property,String detail,Integer total_quantity,Date date_reserve, ReservaType state) throws Exception{
       try{
          Reserve reserve = new Reserve();
          Optional<Property> optionalProperty = repositoryProperty.findById(property);
          if(optionalProperty.isPresent()){
              reserve.setProperty(optionalProperty.get());
          } else{
            throw new IllegalArgumentException("Propiedad  no encontrada");
        }
          Optional<User> OptionalClient = userRepository.findById(client);
          if(OptionalClient.isPresent()){
              reserve.setClient(OptionalClient.get());
          }else{
             throw new IllegalArgumentException("usuario no encontrada"); 
          }
          reserve.setDetail(detail);
          reserve.setTotal_quatity(total_quantity);
          reserve.setDate_reserve(date_reserve);
          reserve.setState(state);
          reserveRepository.save(reserve);
       } catch (Exception e) {
            throw new Exception(e.getMessage());
        }    
       
    }
    
    @Transactional
    public void updateReserve(String reserveId, ReserveRequest request) throws Exception{
        System.out.println("Entrando");
        
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar con el id" + reserveId));
        
        if(request.getClient() != null){
           Optional<User> optionalUser = userRepository.findById(request.getClient());
           if (optionalUser.isPresent()) {
                reserve.setClient(optionalUser.get());
            } else {
                throw new IllegalArgumentException("Usuario  no encontrado");
            }
        }
        if (request.getProperty() != null) {
            Optional<Property> optionalProperty = repositoryProperty.findById(request.getProperty());
            if (optionalProperty.isPresent()) {
                reserve.setProperty(optionalProperty.get());
            } else {
                throw new IllegalArgumentException("Usuario no encontrado");
            }
        }
        if(request.getDetail() != null){
            reserve.setDetail(request.getDetail());
        }
        if(request.getTotal_quatity() != null){
            reserve.setTotal_quatity(request.getTotal_quatity());
        }
        if(request.getDate_reserve() != null){
            reserve.setDate_reserve(request.getDate_reserve());
        }
        if(request.getState() != null){
            reserve.setState(request.getState());
        }
        reserveRepository.save(reserve);
    }
    
    @Transactional
    public void deleteReserve(String reserveId ) throws Exception{
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar con el id" + reserveId));
        reserveRepository.delete(reserve);
    }
    
}
