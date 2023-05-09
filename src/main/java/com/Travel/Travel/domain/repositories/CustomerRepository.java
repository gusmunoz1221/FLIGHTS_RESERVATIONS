package com.Travel.Travel.domain.repositories;

import com.Travel.Travel.domain.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity,String> {
}
