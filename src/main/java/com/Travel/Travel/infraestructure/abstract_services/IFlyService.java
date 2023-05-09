package com.Travel.Travel.infraestructure.abstract_services;

import com.Travel.Travel.api.model.response.FlyDtoResponse;
import java.util.Set;

public interface IFlyService extends CatalogService<FlyDtoResponse>{
 Set<FlyDtoResponse> readByOriginDestiny(String origin, String destiny);
}
