//Dev Duque
package com.airbnb.airbnb.repositories;

import com.airbnb.airbnb.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteRepository extends JpaRepository<Favorite,String> {
    
}
