package com.application.product_service.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.application.product_service.dto.Productdto;
import com.application.product_service.model.Product;
import com.application.product_service.repository.ProductRepository;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; 
    @InjectMocks
    private ProductService productService; 

    @Test
    public void testGetProductBySku_Success() {
        String skuCode = "0001";
        Product mockProduct = new Product();
        mockProduct.setSkuCode(skuCode);
        mockProduct.setName("HP Laptop");
        mockProduct.setPrice(BigDecimal.valueOf(15000));

        Mockito.when(productRepository.findBySkuCode(skuCode))
               .thenReturn(Optional.of(mockProduct));
        Productdto result = productService.getProductBySku(skuCode);


        assertNotNull(result);
        assertEquals("HP Laptop", result.getName());
        assertEquals(skuCode, result.getSkuCode());
        

        Mockito.verify(productRepository, Mockito.times(1)).findBySkuCode(skuCode);
    }
}