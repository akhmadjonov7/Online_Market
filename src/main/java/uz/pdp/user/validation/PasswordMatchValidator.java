package uz.pdp.user.validation;


import com.example.demo.user.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordsMatch, UserDto> {
    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {

        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}