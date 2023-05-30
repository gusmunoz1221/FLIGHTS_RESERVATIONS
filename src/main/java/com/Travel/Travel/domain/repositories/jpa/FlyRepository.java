package com.Travel.Travel.domain.repositories.jpa;

import com.Travel.Travel.domain.entities.jpa.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface FlyRepository extends JpaRepository<FlyEntity,Long> {
    @Query("select f from fly f where f.price < :price")
    Set<FlyEntity> selectLessPrice(BigDecimal price);

    @Query("select f from fly f where f.price between :min and :max")
    Set<FlyEntity> selectBetweenPrice(BigDecimal min, BigDecimal max);

    @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny")
    Set<FlyEntity> selectOriginDestiny(String origin, String destiny);

    @Query("select f from fly f join fetch f.tickets t where t.id = :id")
    Optional<FlyEntity> findByTicketId(UUID id);

    /*
    Set<FlyEntity> selectLessPrice(BigDecimal price);
    Set<FlyEntity> selectPriceBetween(BigDecimal min, BigDecimal max);
    */
}
