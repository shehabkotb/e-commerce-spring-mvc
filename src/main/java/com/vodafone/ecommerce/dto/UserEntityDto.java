package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.enums.Category;
import com.vodafone.ecommerce.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserEntityDto {
    private Long id;
    @NotBlank(message = "userName Should not be Empty")
    private String userName;
    @NotBlank(message = "Email Shouldn't be Null")
    private String email;
    private Role role;
}
