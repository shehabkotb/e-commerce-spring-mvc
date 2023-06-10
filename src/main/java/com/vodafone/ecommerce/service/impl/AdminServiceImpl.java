package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.mapper.UserEntityMapper;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto saveAdmin(UserDto userDto) {
        UserEntity user = UserEntityMapper.mapToUserEntity(userDto);
        user.setRole(Role.ADMIN);
        UserEntity result = userRepository.save(user);
        return UserEntityMapper.mapToUserDto(result);
    }

    @Override
    public List<UserDto> getAllAdmin() {
        List<UserEntity> entityList = userRepository.findByRole(Role.ADMIN);
        return entityList.stream().map(UserEntityMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findAdminById(Long adminId) {
        UserEntity user = userRepository.findById(adminId).get();
        return UserEntityMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateAdmin(UserDto userDto) {
        UserEntity userEntity = UserEntityMapper.mapToUserEntity(userDto);
        UserEntity result = userRepository.save(userEntity);
        return UserEntityMapper.mapToUserDto(result);
    }

    @Override
    public void delete(Long adminId) {
        userRepository.deleteById(adminId);
    }
}
