package com.Travel.Travel.api.controllers.error_handler;

import com.Travel.Travel.api.model.response.BaseErrorResponse;
import com.Travel.Travel.api.model.response.ErrorResponse;
import com.Travel.Travel.util.exceptions.ForBiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForBiddenCustomerHandler {

    @ExceptionHandler(ForBiddenCustomerException.class)
    public BaseErrorResponse handleIdNotFound(ForBiddenCustomerException exception){
        return ErrorResponse.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.FORBIDDEN.name())
                            .code(HttpStatus.FORBIDDEN.value())
                            .build();
    }
}
