package com.Travel.Travel.domain.mappers;

import com.Travel.Travel.api.model.response.FlyDtoResponse;
import com.Travel.Travel.domain.entities.jpa.FlyEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FlyMapper {
    public FlyDtoResponse flyDtoResponse(FlyEntity flyEntity){
        FlyDtoResponse response = new FlyDtoResponse();
        BeanUtils.copyProperties(flyEntity,response);
        return response;
    }
}
