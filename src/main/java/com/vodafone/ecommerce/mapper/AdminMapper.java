package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.AdminDto;
import com.vodafone.ecommerce.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class AdminMapper {
    private final PasswordEncoder passwordEncoder;


    public static UserEntity mapToAdmin(AdminDto adminDto) {


        return UserEntity.builder()
                .id(adminDto.getId())
                .username(adminDto.getUsername())
                .email(adminDto.getEmail())
                .role(adminDto.getRole())
                .password(adminDto.getPassword())
                .build();
    }

    public static AdminDto mapToAdminDto(UserEntity user) {
        return AdminDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}
