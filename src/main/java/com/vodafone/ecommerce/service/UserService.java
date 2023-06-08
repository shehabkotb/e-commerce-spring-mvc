package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface UserService {

    ResponseEntity<?> saveUser(UserEntity user) throws MessagingException;

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
