package com.Travel.Travel.infraestructure.abstract_services;

import com.Travel.Travel.util.sortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

//Recibe un dtoResponse como parametro
public interface CatalogService<R> {
    Page<R> readAll(Integer page, Integer size, sortType sortType);
    Set<R> readLessPrice(BigDecimal price);
    Set<R> readBetweenPrices(BigDecimal min,BigDecimal max);
    String fieldBySort = "price";
}
