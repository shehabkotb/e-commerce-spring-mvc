package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches
public class RegistrationDto {
    private Long id;
    @NotBlank(message = "userName Should not be Empty")
    private String username;
    @NotBlank(message = "Email Shouldn't be Null")
    @Email(regexp = ".+@.+\\..+")
    private String email;
    @NotBlank(message = "password Should not be Empty")
    private String password;
    @NotBlank(message = "password Confirm Should not be Empty")
    private String passwordConfirm;
}
