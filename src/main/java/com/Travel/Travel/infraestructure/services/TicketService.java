package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.api.model.request.TicketDtoRequest;
import com.Travel.Travel.api.model.response.TicketDtoResponse;
import com.Travel.Travel.domain.entities.CustomerEntity;
import com.Travel.Travel.domain.entities.FlyEntity;
import com.Travel.Travel.domain.entities.TicketEntity;
import com.Travel.Travel.domain.mappers.TicketMapper;
import com.Travel.Travel.domain.repositories.CustomerRepository;
import com.Travel.Travel.domain.repositories.FlyRepository;
import com.Travel.Travel.domain.repositories.TicketRepository;
import com.Travel.Travel.infraestructure.abstract_services.ITicketService;
import com.Travel.Travel.infraestructure.helpers.CustomerHelper;
import com.Travel.Travel.util.BestTravelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class TicketService implements ITicketService {
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CustomerHelper customerHelper;

    //declaramos una variable global estatica
    public static final BigDecimal changes_price_percentage = BigDecimal.valueOf(0.25);

    public TicketService(FlyRepository flyRepository, CustomerRepository customerRepository, TicketRepository ticketRepository, TicketMapper ticketMapper, CustomerHelper customerHelper) {
        this.flyRepository = flyRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.customerHelper = customerHelper;
    }
    @Override
    public TicketDtoResponse create(TicketDtoRequest ticketDtoRequest) {

        FlyEntity flyEntity = flyRepository.findById(ticketDtoRequest.getIdFly()).orElseThrow();
        CustomerEntity customerEntity = customerRepository.findById(ticketDtoRequest.getIdClient()).orElseThrow();
        TicketEntity ticketEntity = TicketEntity.builder() // asignamos los datos a los atributos de la entidad ticket con el patron builder
                                                .id(UUID.randomUUID())
                                                .fly(flyEntity)
                                                .customer(customerEntity)
                                                .price(flyEntity.getPrice().add(flyEntity.getPrice().multiply(changes_price_percentage)))//precio del vuelo + 0.25%
                                                .purchaseDate(LocalDate.now())
                                                .departureDate(BestTravelUtil.getRandomSoon())//utiliza la case implementada "bestutil" que devuelve un numero random entre dos numeros
                                                .arrivalDate(BestTravelUtil.getRandomLatter())
                                                .build();

         ticketRepository.save(ticketEntity); // persistimos el ticket en la base de datos
        customerHelper.increase(customerEntity.getDni(),TicketService.class);
        return ticketMapper.ticketEntityToDtoResponse(ticketEntity);
    }

    @Override
    public TicketDtoResponse read(UUID id) {

        TicketEntity ticketEntity = ticketRepository.findById(id).orElseThrow();
        return ticketMapper.ticketEntityToDtoResponse(ticketEntity);
    }

    @Override
    public TicketDtoResponse update(TicketDtoRequest request, UUID id) {

        FlyEntity flyEntity = flyRepository.findById(request.getIdFly()).orElseThrow();
        TicketEntity ticketEntity = ticketRepository.findById(id).orElseThrow();
            ticketEntity.setFly(flyEntity);
            ticketEntity.setPrice(flyEntity.getPrice().add(flyEntity.getPrice().multiply(changes_price_percentage))); //precio del vuelo + 0.25%
            ticketEntity.setDepartureDate(BestTravelUtil.getRandomSoon());//utiliza la case implementada "bestutil" que devuelve un numero random entre dos numeros
            ticketEntity.setArrivalDate(BestTravelUtil.getRandomLatter());
            ticketRepository.save(ticketEntity);
        return ticketMapper.ticketEntityToDtoResponse(ticketEntity);
    }

    @Override
    public void delete(UUID id) {
        TicketEntity ticketEntity = ticketRepository.findById(id).orElseThrow();
        customerHelper.decrease(ticketEntity.getCustomer().getDni(),TicketService.class);
        ticketRepository.delete(ticketEntity);
    }

    @Override
    public BigDecimal findPrice(Long idFly) {
        FlyEntity flyEntity = flyRepository.findById(idFly).orElseThrow();
        return flyEntity.getPrice().add(flyEntity.getPrice().multiply(changes_price_percentage));
    }
}