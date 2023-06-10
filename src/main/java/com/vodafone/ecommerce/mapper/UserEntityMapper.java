package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.model.UserEntity;

public class UserEntityMapper {

    public static UserEntity mapToUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .password(userDto.getPassword())
                .build();
    }

    public static UserDto mapToUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}
