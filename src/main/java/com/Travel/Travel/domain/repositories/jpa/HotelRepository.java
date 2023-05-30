package com.Travel.Travel.domain.repositories.jpa;

import com.Travel.Travel.domain.entities.jpa.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    //hoteles con el nombre menor que price
    Set<HotelEntity> findByPriceLessThan(BigDecimal price);

    //hoteles que estan entre precio max y min
    Set<HotelEntity> findByPriceBetween(BigDecimal min, BigDecimal Max);

    //hoteles que tengan una rating mayor a
    Set<HotelEntity> findByRatingGreaterThan(Integer rating);

    // realiza un join con hotel y reservation es decir que hotel corresponde a dicho id
    Optional<HotelEntity> findByReservationId(UUID id);
}
