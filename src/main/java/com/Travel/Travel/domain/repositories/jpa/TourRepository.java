package com.Travel.Travel.domain.repositories.jpa;

import com.Travel.Travel.domain.entities.jpa.TourEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<TourEntity,Long> {
}
