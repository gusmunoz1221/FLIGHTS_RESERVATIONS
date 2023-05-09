package com.Travel.Travel.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder //al ser clase abstracta debemos colocar super builder -> es decir es una clase que va heredar
public class BaseErrorResponse {
    private String status;
    private Integer code;
}
