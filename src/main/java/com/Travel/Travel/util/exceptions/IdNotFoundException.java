package com.Travel.Travel.util.exceptions;
//RuntimeException es la clase base de las exceptiones
public class IdNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE ="Record no exist in %s";

    public IdNotFoundException(String tableName){
        super(String.format(ERROR_MESSAGE,tableName));
    }
}
