package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.UserDto;

import java.util.List;

public interface AdminService {
    UserDto saveAdmin(UserDto userDto);

    List<UserDto> getAllAdmin();

    UserDto findAdminById(Long adminId);

    UserDto updateAdmin(UserDto userDto, Long adminId);

    void deleteAdmin(Long adminId);
}
