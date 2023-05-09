package com.Travel.Travel.util.exceptions;

public class ForBiddenCustomerException extends RuntimeException{

    //exception personalizada
    public ForBiddenCustomerException(){
        super("This customer is blocked");
    }
}
