package com.vodafone.ecommerce.exception;

import com.vodafone.ecommerce.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InsufficientBalanceException.class)
        public ResponseEntity<ErrorMessage> InsufficientBalanceException(InsufficientBalanceException insufficientBalanceException){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(insufficientBalanceException.getStatus().getReasonPhrase());
        errorMessage.setMessage(insufficientBalanceException.getMessage());
        return new ResponseEntity<>(errorMessage,insufficientBalanceException.getStatus());


        }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> NotFoundException(NotFoundException notFoundException){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(notFoundException.getStatus().getReasonPhrase());
        errorMessage.setMessage(notFoundException.getMessage());
        return new ResponseEntity<>(errorMessage,notFoundException.getStatus());


    }

}
