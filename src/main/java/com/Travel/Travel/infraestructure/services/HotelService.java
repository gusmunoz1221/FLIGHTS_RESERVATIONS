package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.api.model.response.HotelDtoResponse;
import com.Travel.Travel.domain.mappers.HotelMapper;
import com.Travel.Travel.domain.repositories.HotelRepository;
import com.Travel.Travel.infraestructure.abstract_services.IHotelService;
import com.Travel.Travel.util.sortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class HotelService implements IHotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public Page<HotelDtoResponse> readAll(Integer page, Integer size, sortType sortType) {
        PageRequest pageRequest=null;
        switch (sortType){
            case NONE  -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest = PageRequest.of(page,size, Sort.by(fieldBySort).ascending());
            case UPPER -> pageRequest =  PageRequest.of(page,size, Sort.by(fieldBySort).descending());}

        return hotelRepository.findAll(pageRequest).map(hotelMapper::hotelDtoResponse);
    }

    //***  utilizamos los metodos brindados por spring mediante   JPA ***

    @Override
    public Set<HotelDtoResponse> readLessPrice(BigDecimal price) {
        return  hotelRepository.findByPriceLessThan(price).stream()
                                                    .map(hotelMapper::hotelDtoResponse)
                                                    .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelDtoResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return  hotelRepository.findByPriceBetween(min,max).stream()
                .map(hotelMapper::hotelDtoResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelDtoResponse> readGreaterThan(Integer rating) {
        return  hotelRepository.findByRatingGreaterThan(rating).stream()
                .map(hotelMapper::hotelDtoResponse)
                .collect(Collectors.toSet());
    }
}
