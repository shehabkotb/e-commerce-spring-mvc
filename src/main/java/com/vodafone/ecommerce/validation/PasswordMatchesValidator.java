package com.vodafone.ecommerce.validation;

import com.vodafone.ecommerce.dto.RegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegistrationDto registrationDto = (RegistrationDto) obj;
        boolean valid = registrationDto.getPassword().equals(registrationDto.getPasswordConfirm());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("passwordConfirm").addConstraintViolation();
        }
        return valid;
    }
}
