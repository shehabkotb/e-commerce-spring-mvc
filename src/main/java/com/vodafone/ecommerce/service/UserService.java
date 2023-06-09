package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.UserEntityDto;
import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.http.ResponseEntity;
import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    ResponseEntity<?> saveUser(UserEntity user) throws MessagingException;

    ResponseEntity<?> confirmEmail(String confirmationToken);


    UserEntity saveAdmin(UserEntityDto userEntityDto);
    List<UserEntity> getAllAdmin();
}
