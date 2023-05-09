package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.api.model.request.ReservationDtoRequest;
import com.Travel.Travel.api.model.response.ReservationDtoResponse;
import com.Travel.Travel.domain.entities.CustomerEntity;
import com.Travel.Travel.domain.entities.HotelEntity;
import com.Travel.Travel.domain.entities.ReservationEntity;
import com.Travel.Travel.domain.mappers.ReservationMapper;
import com.Travel.Travel.domain.repositories.CustomerRepository;
import com.Travel.Travel.domain.repositories.HotelRepository;
import com.Travel.Travel.domain.repositories.ReservationRepository;
import com.Travel.Travel.infraestructure.abstract_services.IReservationService;
import com.Travel.Travel.infraestructure.helpers.BlackListHelper;
import com.Travel.Travel.infraestructure.helpers.CustomerHelper;
import com.Travel.Travel.util.exceptions.IdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
public class ReservationService implements IReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationMapper reservationMapper;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    public static final BigDecimal changes_price_percentage = BigDecimal.valueOf(0.20);

    public ReservationService(ReservationRepository reservationRepository, CustomerRepository customerRepository, HotelRepository hotelRepository, ReservationMapper reservationMapper, CustomerHelper customerHelper, BlackListHelper blackListHelper) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.hotelRepository = hotelRepository;
        this.reservationMapper = reservationMapper;
        this.customerHelper = customerHelper;
        this.blackListHelper = blackListHelper;
    }


    @Override
    public ReservationDtoResponse create(ReservationDtoRequest reservationDtoRequest) {
        blackListHelper.isBlackListCustomer(reservationDtoRequest.getIdClient());//valida si el cliente esta en la lista negra

        HotelEntity hotelEntity = hotelRepository.findById(reservationDtoRequest.getIdHotel())
                                                 .orElseThrow(()-> new IdNotFoundException("hotel"));
        CustomerEntity customerEntity = customerRepository.findById(reservationDtoRequest.getIdClient())
                                                          .orElseThrow(()-> new IdNotFoundException("customer"));
        ReservationEntity reservationEntity = ReservationEntity.builder()
                                                                .id(UUID.randomUUID())
                                                                .hotel(hotelEntity)
                                                                .customer(customerEntity)
                                                                .totalDays(reservationDtoRequest.getTotalDays())
                                                                .dateTimeReservation(LocalDateTime.now())
                                                                .dateStart(LocalDate.now())
                                                                .dateEnd(LocalDate.now().plusDays(reservationDtoRequest.getTotalDays()))
                                                                .price(hotelEntity.getPrice().add(hotelEntity.getPrice().multiply(changes_price_percentage)))
                                                                .build();

        reservationRepository.save(reservationEntity);
        customerHelper.increase(customerEntity.getDni(),ReservationService.class);
        return reservationMapper.reservationEntityToDtoResponse(reservationEntity);
    }

    @Override
    public ReservationDtoResponse read(UUID id) {
        ReservationEntity reservationEntity = reservationRepository.findById(id)
                                                                    .orElseThrow(()-> new IdNotFoundException("reservation"));
        return reservationMapper.reservationEntityToDtoResponse(reservationEntity);
    }

    @Override
    public ReservationDtoResponse update(ReservationDtoRequest reservationDtoRequest, UUID id) {

        HotelEntity hotelEntity = hotelRepository.findById(reservationDtoRequest.getIdHotel())
                                                 .orElseThrow(()-> new IdNotFoundException("hotel"));
        ReservationEntity reservationEntity = reservationRepository.findById(id)
                                                                   .orElseThrow(()-> new IdNotFoundException("reservation"));
            reservationEntity.setHotel(hotelEntity);
            reservationEntity.setTotalDays(reservationDtoRequest.getTotalDays());
            reservationEntity.setDateTimeReservation(LocalDateTime.now());
            reservationEntity.setDateStart(LocalDate.now());
            reservationEntity.setDateEnd(LocalDate.now().plusDays(reservationDtoRequest.getTotalDays()));
            reservationEntity.setPrice(hotelEntity.getPrice().add(hotelEntity.getPrice().multiply(changes_price_percentage)));
            reservationRepository.save(reservationEntity);
       return reservationMapper.reservationEntityToDtoResponse(reservationEntity);
    }

    @Override
    public void delete(UUID id) {
        ReservationEntity reservationEntity = reservationRepository.findById(id)
                                                                   .orElseThrow(()-> new IdNotFoundException("reservation"));
        customerHelper.decrease(reservationEntity.getCustomer().getDni(),ReservationService.class);
        reservationRepository.delete(reservationEntity);
    }

    @Override
    public BigDecimal findPrice(Long idHotel) {
        HotelEntity hotelEntity  = hotelRepository.findById(idHotel)
                                                  .orElseThrow(()-> new IdNotFoundException("hotel"));
        return hotelEntity.getPrice().add(hotelEntity.getPrice().multiply(changes_price_percentage));
    }
}
