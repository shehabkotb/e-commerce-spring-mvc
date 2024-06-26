package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.mapper.UserEntityMapper;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto saveAdmin(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (userRepository.existsByusername(userDto.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        UserEntity user = UserEntityMapper.mapToUserEntity(userDto);
        user.setRole(Role.ADMIN);
        user.setLoginFailureCount(0);
        user.setStatus(Status.ACTIVE);
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
        UserEntity user = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("This Admin doesn't exist."));
        return UserEntityMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateAdmin(UserDto userDto, Long adminId) {
        UserEntity adminToUpdate = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("This Admin doesn't exist."));
        if (userRepository.existsByEmail(userDto.getEmail()) && !userDto.getEmail().equals(adminToUpdate.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (userRepository.existsByusername(userDto.getUsername()) && !userDto.getUsername().equals(adminToUpdate.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        userDto.setId(adminId);
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        UserEntity userEntity = UserEntityMapper.mapToUserEntity(userDto);
        userEntity.setLoginFailureCount(0);
        userEntity.setStatus(Status.ACTIVE);
        UserEntity result = userRepository.save(userEntity);

        return UserEntityMapper.mapToUserDto(result);
    }

    @Override
    public void deleteAdmin(Long adminId) {
        userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("This Admin doesn't exist."));
        userRepository.deleteById(adminId);
    }
}
