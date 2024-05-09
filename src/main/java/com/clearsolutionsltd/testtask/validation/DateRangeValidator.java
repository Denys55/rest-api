package com.clearsolutionsltd.testtask.validation;

import com.clearsolutionsltd.testtask.exception.CustomValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidateDateRange, Object> {

    private String startsAt;
    private String endsAt;

    @Override
    public void initialize(ValidateDateRange constraintAnnotation) {
        startsAt = constraintAnnotation.start();
        endsAt = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            var fieldStart = value.getClass().getDeclaredField(startsAt);
            fieldStart.setAccessible(true);

            var startDate = (LocalDate) fieldStart.get(value);

            var fieldEnd = value.getClass().getDeclaredField(endsAt);
            fieldEnd.setAccessible(true);

            var endDate = (LocalDate) fieldEnd.get(value);

            if(startDate != null && endDate == null) {
                return true;
            }

            var isStartBeforeEnd = startDate.isBefore(endDate);

            if (!isStartBeforeEnd) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(startsAt)
                        .addConstraintViolation();
            }

            return isStartBeforeEnd;

        } catch (java.lang.NoSuchFieldException exception) {
            throw new CustomValidationException("Field not found");
        } catch (java.lang.IllegalAccessException exception) {
            throw new CustomValidationException("Field not accessible");
        }
    }
}
