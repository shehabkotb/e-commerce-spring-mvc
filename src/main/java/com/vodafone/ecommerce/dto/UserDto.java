package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(message = "userName Should not be Empty")
    private String username;
    @NotBlank(message = "Email Shouldn't be Null")
    @Email
    private String email;
    private Role role;
    @NotBlank(message = "password Should not be Empty")
    private String password;
}
