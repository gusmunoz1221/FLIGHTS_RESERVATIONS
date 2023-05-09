package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.api.model.request.TourDtoRequest;
import com.Travel.Travel.api.model.response.TourDtoResponse;
import com.Travel.Travel.domain.entities.*;
import com.Travel.Travel.domain.repositories.CustomerRepository;
import com.Travel.Travel.domain.repositories.FlyRepository;
import com.Travel.Travel.domain.repositories.HotelRepository;
import com.Travel.Travel.domain.repositories.TourRepository;
import com.Travel.Travel.infraestructure.abstract_services.ITourService;
import com.Travel.Travel.infraestructure.helpers.BlackListHelper;
import com.Travel.Travel.infraestructure.helpers.CustomerHelper;
import com.Travel.Travel.infraestructure.helpers.TourHelper;
import com.Travel.Travel.util.exceptions.IdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class TourService implements ITourService {
    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    public TourService(TourRepository tourRepository, FlyRepository flyRepository, HotelRepository hotelRepository, CustomerRepository customerRepository, TourHelper tourHelper, CustomerHelper customerHelper, BlackListHelper blackListHelper) {
        this.tourRepository = tourRepository;
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.customerRepository = customerRepository;
        this.tourHelper = tourHelper;
        this.customerHelper = customerHelper;
        this.blackListHelper = blackListHelper;
    }

    @Override
    public TourDtoResponse create(TourDtoRequest tourDtoRequest) {
        blackListHelper.isBlackListCustomer(tourDtoRequest.getCustomerId()); //valida si el cliente esta en la lista negra

        CustomerEntity customerEntity = customerRepository.findById(tourDtoRequest.getCustomerId())
                                                          .orElseThrow(()-> new IdNotFoundException("customer"));

        //instanciando el hashset de FLIGHTS
        HashSet<FlyEntity> flights = new HashSet<FlyEntity>();
        tourDtoRequest.getFlights().forEach(fly -> flights.add(flyRepository.findById(fly.getId())
                                                                            .orElseThrow(()-> new IdNotFoundException("tour"))));

        //instanciamos el hashmap de HOTELS
        HashMap<HotelEntity,Integer> hotels = new HashMap<HotelEntity,Integer>();
        tourDtoRequest.getHotels().forEach(hotel -> hotels.put(hotelRepository.findById(hotel.getId())
                                                                              .orElseThrow(()-> new IdNotFoundException("hotel")),
                                                                hotel.getTotalDays()
                                                                )
                                            );
        TourEntity  tourEntity =  TourEntity.builder()
                                            .tickets(tourHelper.createTicket(flights,customerEntity))
                                            .reservations(tourHelper.createReservation(hotels,customerEntity))
                                            .customer(customerEntity)
                                            .build();
        tourRepository.save(tourEntity);
        customerHelper.increase(customerEntity.getDni(),TourService.class);
        return TourDtoResponse.builder()
                                .reservationIds(tourEntity.getReservations().stream()
                                                                            .map(ReservationEntity::getId)
                                                                            .collect(Collectors.toSet()))
                                .ticketIds(tourEntity.getTickets().stream()
                                                                  .map(TicketEntity::getId)
                                                                  .collect(Collectors.toSet()))
                                .id(tourEntity.getId())
                                .build();
    }

    @Override
    public TourDtoResponse read(Long id) {
        TourEntity tourEntity = tourRepository.findById(id).orElseThrow();
        return TourDtoResponse.builder()
                              .reservationIds(tourEntity.getReservations().stream()
                                                                          .map(ReservationEntity::getId)
                                                                          .collect(Collectors.toSet()))
                              .ticketIds(tourEntity.getTickets().stream()
                                                                .map(TicketEntity::getId)
                                                                .collect(Collectors.toSet()))
                              .id(tourEntity.getId())
                              .build();
    }

    @Override
    public void delete(Long id) {
        TourEntity tourEntity = tourRepository.findById(id)
                                              .orElseThrow(()-> new IdNotFoundException("tour"));
        customerHelper.decrease(tourEntity.getCustomer().getDni(),TourService.class);
       tourRepository.delete(tourEntity);
    }

    @Override
    public UUID addTicket(Long tourId,Long flyId ) {
        TourEntity tourEntity = tourRepository.findById(tourId).orElseThrow();
        FlyEntity flyEntity = flyRepository.findById(flyId).orElseThrow();
        TicketEntity ticketEntity = tourHelper.createTicket(flyEntity,tourEntity.getCustomer());
        tourEntity.addTicket(ticketEntity);
        tourRepository.save(tourEntity);
        return ticketEntity.getId();
    }

    @Override
    public void removeReservation(Long tourId,UUID reservationId) {
        TourEntity tourEntity = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));
        tourEntity.removeReservation(reservationId);
        tourRepository.save(tourEntity);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId,Integer totalDays) {
        TourEntity tourEntity = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));
        HotelEntity hotelEntity = hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException("hotel"));
        ReservationEntity reservationEntity = tourHelper.createReservation(hotelEntity,tourEntity.getCustomer(),totalDays);
        tourEntity.addReservation(reservationEntity);
        tourRepository.save(tourEntity);
        return reservationEntity.getId();
    }

    @Override//eliminamos el ticket del tour con el id -> tourid
    public void removeTicket(Long tourId,UUID ticketId) {
        TourEntity tourEntity = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("tour"));
        tourEntity.removeTicket(ticketId);
        tourRepository.save(tourEntity);
    }
}
