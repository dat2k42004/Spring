package com.example.demo.service.validator;

import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.RegisterDTO;
import com.example.demo.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

     private final UserService userService;

     public RegisterValidator(UserService userService) {
          this.userService = userService;
     }

     @Override
     public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
          boolean valid = true;

          // check password field match
          if (!user.getConfirmPassword().equals(user.getPassword())) {
               context.buildConstraintViolationWithTemplate("Password not match")
                         .addPropertyNode("confirmPassword")
                         .addConstraintViolation()
                         .disableDefaultConstraintViolation();
               valid = false;
          }

          // check email
          if (this.userService.checkEmailExit(user.getEmail())) {
               context.buildConstraintViolationWithTemplate("Email already existed")
                         .addPropertyNode("email")
                         .addConstraintViolation()
                         .disableDefaultConstraintViolation();
               valid = false;
          }

          return valid;
     }

}
