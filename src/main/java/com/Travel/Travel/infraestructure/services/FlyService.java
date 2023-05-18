package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.api.model.response.FlyDtoResponse;
import com.Travel.Travel.domain.mappers.FlyMapper;
import com.Travel.Travel.domain.repositories.FlyRepository;
import com.Travel.Travel.infraestructure.abstract_services.IFlyService;
import com.Travel.Travel.util.sortType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)//CLASES DE TIPO CATALOGOS
@Service
public class FlyService implements IFlyService {
    private final FlyRepository flyRepository;
    private final FlyMapper flyMapper;

    public FlyService(FlyRepository flyRepository, FlyMapper flyMapper) {
        this.flyRepository = flyRepository;
        this.flyMapper = flyMapper;
    }

    @Override // retorna la pagina mediante pageRequest con sus parametros numero de pagina,tamaño, y el opcional parametro de ordenamiento
    public Page<FlyDtoResponse> readAll(Integer page, Integer size, sortType sortType) {
        PageRequest pageRequest=null;
        switch (sortType){
            case NONE  -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest = PageRequest.of(page,size, Sort.by(fieldBySort).ascending());
            case UPPER -> pageRequest =  PageRequest.of(page,size, Sort.by(fieldBySort).descending());}

        return flyRepository.findAll(pageRequest).map(flyMapper::flyDtoResponse);
    }

    @Override //retorna un set con precios menores al parametro recibido
    public Set<FlyDtoResponse> readLessPrice(BigDecimal price) {
        return  flyRepository.selectLessPrice(price).stream()
                                                    .map(flyMapper::flyDtoResponse)
                                                    .collect(Collectors.toSet());
    }

    @Override//retorna un set con precios entre max y min
    public Set<FlyDtoResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return  flyRepository.selectBetweenPrice(min,max).stream()
                                                         .map(flyMapper::flyDtoResponse)
                                                         .collect(Collectors.toSet());
    }

    @Override//retorna un set con origen y destino seleccionado
    public Set<FlyDtoResponse> readByOriginDestiny(String origin, String destiny) {
        return  flyRepository.selectOriginDestiny(origin,destiny).stream()
                                                                 .map(flyMapper::flyDtoResponse)
                                                                 .collect(Collectors.toSet());
    }
}