package Proyect.IoTParkers.shared.infrastructure.validation;

import Proyect.IoTParkers.shared.infrastructure.security.PasswordPatterns;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null) return false; // fuerza presencia
        return value.matches(PasswordPatterns.STRONG_PASSWORD_REGEX);
    }
}