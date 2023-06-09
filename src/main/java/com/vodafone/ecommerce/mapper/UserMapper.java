package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.UserEntityDto;
import com.vodafone.ecommerce.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;


    public static UserEntity mapToUser(UserEntityDto userEntityDto) {


        return UserEntity.builder()
                .username(userEntityDto.getUsername())
                .email(userEntityDto.getEmail())
                .role(userEntityDto.getRole())
                .password(userEntityDto.getPassword())
                .build();
    }

    public static UserEntityDto mapToUserDto(UserEntity user) {
        return UserEntityDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}
