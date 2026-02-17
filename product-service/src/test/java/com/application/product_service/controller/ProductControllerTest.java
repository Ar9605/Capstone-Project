package com.application.product_service.controller;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.application.product_service.dto.Productdto;
import com.application.product_service.service.ProductService;

// Notice: No @WebMvcTest! We are using pure Java and Mockito.
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService; // Fake the service layer

    @InjectMocks
    private ProductController productController; // Inject the fake service into the real controller

    @Test
    public void testCreateProduct_Success() {
        // 1. Arrange: Create a fake request payload
        Productdto requestDto = new Productdto();
        requestDto.setSkuCode("MOCK-SKU-123");
        requestDto.setName("Pure Java Product");
        requestDto.setPrice(BigDecimal.valueOf(500));

        // 2. Act & Assert: Call the controller directly. 
        // Since it returns a 201 Created status, we just verify it doesn't crash.
        assertDoesNotThrow(() -> {
            productController.createProduct(requestDto);
        });

        // 3. Verify: Check that the Controller actually handed the data down to the Service layer
        Mockito.verify(productService, Mockito.times(1)).createProduct(requestDto);
    }
}
