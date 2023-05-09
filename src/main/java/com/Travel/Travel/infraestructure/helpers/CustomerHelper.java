package com.Travel.Travel.infraestructure.helpers;

import com.Travel.Travel.domain.entities.CustomerEntity;
import com.Travel.Travel.domain.repositories.CustomerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CustomerHelper {
    private final CustomerRepository customerRepository;

    public CustomerHelper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // incrementos en uno, de totales en diferentes clases
    public void increase(String customerId, Class<?> type){
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow();

        switch (type.getSimpleName()){
            case "TourService" -> customerEntity.setTotalTours(customerEntity.getTotalTours()+1);
            case "TicketService" -> customerEntity.setTotalFlights(customerEntity.getTotalFlights()+1);
            case "ReservationService" -> customerEntity.setTotalLodgings(customerEntity.getTotalLodgings()+1);
        }
        customerRepository.save(customerEntity);
    }

    // decrementa en uno, de totales en diferentes clases
    public void decrease(String customerId, Class<?> type){
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow();

        switch (type.getSimpleName()){
            case "TourService" -> customerEntity.setTotalTours(customerEntity.getTotalTours()-1);
            case "TicketService" -> customerEntity.setTotalFlights(customerEntity.getTotalFlights()-1);
            case "ReservationService" -> customerEntity.setTotalLodgings(customerEntity.getTotalLodgings()-1);
        }
        customerRepository.save(customerEntity);
    }
}
