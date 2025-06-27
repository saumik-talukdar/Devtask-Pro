package com.saumik.devtask_pro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Enum<?>[] enumConstants;

    @Override
    public void initialize(EnumValue annotation) {
        enumConstants = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // Let @NotNull handle required fields

        return Arrays.stream(enumConstants)
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
