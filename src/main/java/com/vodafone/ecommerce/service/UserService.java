package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.model.UserEntity;

import javax.mail.MessagingException;

public interface UserService {

    UserDto registerUser(RegistrationDto registrationDto) throws MessagingException;

    void resetAccount(String email) throws MessagingException;

    void confirmEmail(String confirmationToken);
    UserEntity getUserById(Long userId);
}
