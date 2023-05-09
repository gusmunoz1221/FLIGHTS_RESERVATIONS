package com.Travel.Travel.api.controllers.error_handler;

import com.Travel.Travel.api.model.response.BaseErrorResponse;
import com.Travel.Travel.api.model.response.ErrorResponse;
import com.Travel.Travel.api.model.response.ErrorsResponse;
import com.Travel.Travel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

//STATUS DEL TIPO 400 SON ERRRORES DEL LADO DEL CLIENTE, 500 DEL LADO DEL SERVIDOR
@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception){
        return ErrorResponse.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .code(HttpStatus.BAD_REQUEST.value())
                            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleIdNotFound(MethodArgumentNotValidException exception){
        ArrayList<String> errors = new ArrayList<>();
        exception.getAllErrors()
                 .forEach(error -> errors.add(error.getDefaultMessage()) );
        return ErrorsResponse.builder()
                             .errors(errors)
                             .status(HttpStatus.BAD_REQUEST.name())
                             .code(HttpStatus.BAD_REQUEST.value())
                             .build();
    }
}
