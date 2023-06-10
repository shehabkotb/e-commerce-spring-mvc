package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.model.UserEntity;

import javax.mail.MessagingException;

public interface UserService {

    UserDto saveUser(UserEntity user) throws MessagingException;

    void confirmEmail(String confirmationToken);
}
