package com.application.product_service.dto;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProductDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        // Spin up the standard Java validation engine
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testProductDto_ValidData_PassesValidation() {
        Productdto dto = new Productdto();
        dto.setSkuCode("00060");
        dto.setName("Product");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setDescription("Description");

        // Validate the DTO
        Set<ConstraintViolation<Productdto>> violations = validator.validate(dto);
        
        // Assert that there are NO errors
        assertTrue(violations.isEmpty(), "Valid DTO should have no validation errors");
    }

    @Test
    public void testProductDto_MissingData_FailsValidation() {
        Productdto dto = new Productdto();
        // Leaving fields null intentionally to trigger validation errors

        Set<ConstraintViolation<Productdto>> violations = validator.validate(dto);
        
        // Assert that the validator successfully caught the missing data
        assertFalse(violations.isEmpty(), "Empty DTO should trigger validation errors");
    }
}