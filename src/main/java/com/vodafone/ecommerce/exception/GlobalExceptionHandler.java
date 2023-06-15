package com.vodafone.ecommerce.exception;

import com.vodafone.ecommerce.security.CustomUserDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InsufficientBalanceException.class)
    public ModelAndView InsufficientBalanceException(InsufficientBalanceException insufficientBalanceException) {
        ModelAndView mav = new ModelAndView();
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mav.setViewName("redirect:/checkout/" + customUserDetails.getId() + "?failed&message=Insufficient+Balance");
        return mav;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView NotFoundException(NotFoundException notFoundException){
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", notFoundException.getMessage());
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 404);
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(InvalidCardException.class)
    public ModelAndView InvalidException(InvalidCardException invalidCardException, WebRequest webRequest) {
        ModelAndView mav = new ModelAndView();
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mav.setViewName("redirect:/checkout/" + customUserDetails.getId() + "?failed&message=Invalid+Card+Details");
        return mav;
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ModelAndView handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
//        String message = "You do not have permission to perform this action.";
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 403);
        mav.setViewName("forbiddenAccess");
        return mav;
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected ModelAndView handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request) {
//        String message = "you Enter a wrong URL.";
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("timestamp", new Date().toString());
        mav.addObject("status", 405);
        mav.setViewName("wrong-url");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
