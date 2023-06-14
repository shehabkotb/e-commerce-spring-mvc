package com.vodafone.ecommerce.exception;

import com.vodafone.ecommerce.model.ErrorMessage;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public ModelAndView NotFoundException(NotFoundException notFoundException){
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", notFoundException.getMessage());
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 404);
        mav.setViewName("notFound-exception");
        return mav;
    }
    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<ErrorMessage> InvalidException(InvalidCardException invalidCardException){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(invalidCardException.getStatus().getReasonPhrase());
        errorMessage.setMessage(invalidCardException.getMessage());
        return new ResponseEntity<>(errorMessage,invalidCardException.getStatus());
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    protected ModelAndView handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
//        String message = "You do not have permission to perform this action.";
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 403);
        mav.setViewName("forbiddenAccess");
        return mav;
    }

    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    protected ModelAndView handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request) {
//        String message = "you Enter a wrong URL.";
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 405);
        mav.setViewName("wrong-url");
        return mav;
    }

}
