package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Confirmation Token")
public class InvalidConfirmationToken extends RuntimeException {

}
