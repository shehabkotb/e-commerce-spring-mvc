package com.vodafone.ecommerce.exception;


import org.springframework.http.HttpStatus;


public  class InvalidCardException extends RuntimeException {

    public InvalidCardException(String message){

        super(message);
    }
    public  HttpStatus getStatus(){
        return HttpStatus.FORBIDDEN;
    }


}
