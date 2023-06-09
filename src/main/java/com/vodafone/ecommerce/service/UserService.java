package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.AdminDto;
import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.http.ResponseEntity;
import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    ResponseEntity<?> saveUser(UserEntity user) throws MessagingException;

    ResponseEntity<?> confirmEmail(String confirmationToken);


    UserEntity saveAdmin(AdminDto adminDto);
    List<UserEntity> getAllAdmin();

    AdminDto findAdminById(Long adminId);

    void updateClub(AdminDto admin);

    void delete(Long adminId);
}
