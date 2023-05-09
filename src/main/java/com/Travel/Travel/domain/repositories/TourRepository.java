package com.Travel.Travel.domain.repositories;

import com.Travel.Travel.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<TourEntity,Long> {
}
