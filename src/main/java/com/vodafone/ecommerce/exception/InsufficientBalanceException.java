package com.vodafone.ecommerce.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public  class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message){

        super(message);
    }
    public  HttpStatus getStatus(){
        return HttpStatus.BAD_REQUEST;
    }


}
