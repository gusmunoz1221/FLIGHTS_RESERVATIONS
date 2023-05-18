package com.Travel.Travel.infraestructure.helpers;

import com.Travel.Travel.domain.entities.*;
import com.Travel.Travel.domain.repositories.ReservationRepository;
import com.Travel.Travel.domain.repositories.TicketRepository;
import com.Travel.Travel.infraestructure.services.ReservationService;
import com.Travel.Travel.infraestructure.services.TicketService;
import com.Travel.Travel.util.BestTravelUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
//llamamos a tour helper para no llamar a los services de TICKET Y RESERVATION
public class TourHelper {
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public TourHelper(TicketRepository ticketRepository, ReservationRepository reservationRepository) {
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
    }

    /*
     *   RECORRO TODA LA LISTA DE FLY, EN UN FOREACH INSTANCIO CADA VUELO DE LA LISTA Y LO AÑADO A AL HASHSET "RESPONSE"
     *  CUANDO PERSISTIMOS EN LA BD
     * */
    public Set<TicketEntity> createTicket(Set<FlyEntity> flight, CustomerEntity customer) {

        HashSet<TicketEntity> response = new HashSet<TicketEntity>(flight.size());
        flight.forEach(fly -> {
            TicketEntity ticketEntity = TicketEntity.builder() // asignamos los datos a los atributos de la entidad ticket con el patron builder
                                                    .id(UUID.randomUUID())
                                                    .fly(fly)
                                                    .customer(customer)
                                                    .price(fly.getPrice()
                                                              .add(fly.getPrice()
                                                                      .multiply(TicketService.changes_price_percentage)))//precio del vuelo + 0.25%
                                                    .purchaseDate(LocalDate.now())
                                                    .departureDate(BestTravelUtil.getRandomSoon())//utiliza la case implementada "bestutil" que devuelve un numero random entre dos numeros
                                                    .arrivalDate(BestTravelUtil.getRandomLatter())
                                                    .build();

            response.add(ticketRepository.save(ticketEntity));
        });
        return response;
    }

    public Set<ReservationEntity> createReservation(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer) {

        HashSet<ReservationEntity> response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) ->
                                {
                                ReservationEntity reservationEntity = ReservationEntity.builder()
                                                                                        .id(UUID.randomUUID())
                                                                                        .hotel(hotel)
                                                                                        .customer(customer)
                                                                                        .totalDays(totalDays)
                                                                                        .dateTimeReservation(LocalDateTime.now())
                                                                                        .dateStart(LocalDate.now())
                                                                                        .dateEnd(LocalDate.now().plusDays(totalDays))
                                                                                        .price(hotel.getPrice()
                                                                                                    .add(hotel.getPrice()
                                                                                                              .multiply(ReservationService.changes_price_percentage)))
                                                                                        .build();

            response.add(reservationRepository.save(reservationEntity));
        });
        return response;
    }
//un servicio no debe llamar a otro servicio
    public TicketEntity createTicket(FlyEntity flyEntity,CustomerEntity customerEntity){
        TicketEntity ticketEntity = TicketEntity.builder() // asignamos los datos a los atributos de la entidad ticket con el patron builder
                .id(UUID.randomUUID())
                .fly(flyEntity)
                .customer(customerEntity)
                .price(flyEntity.getPrice().add(flyEntity.getPrice().multiply(TicketService.changes_price_percentage)))//precio del vuelo + 0.25%
                .purchaseDate(LocalDate.now())
                .departureDate(BestTravelUtil.getRandomSoon())//utiliza la case implementada "bestutil" que devuelve un numero random entre dos numeros
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .build();
        return ticketRepository.save(ticketEntity);
    }

    //**********************RESERVATIONS****************************************

    public ReservationEntity createReservation(HotelEntity hotelEntity,CustomerEntity customerEntity,Integer totalDatys){
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotelEntity)
                .customer(customerEntity)
                .totalDays(totalDatys)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDatys))
                .price(hotelEntity.getPrice().add(hotelEntity.getPrice().multiply(ReservationService.changes_price_percentage)))
                .build();
        return reservationRepository.save(reservationEntity);
    }

}
