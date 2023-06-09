package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.UserEntityDto;
import com.vodafone.ecommerce.model.UserEntity;

public class UserMapper {
    public static UserEntity mapToUser(UserEntityDto userEntityDto) {
        return UserEntity.builder()
                .username(userEntityDto.getUsername())
                .email(userEntityDto.getEmail())
                .role(userEntityDto.getRole())
                .build();
    }

    public static UserEntityDto mapToUserDto(UserEntity user) {
        return UserEntityDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
