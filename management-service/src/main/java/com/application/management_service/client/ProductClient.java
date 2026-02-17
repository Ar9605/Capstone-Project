package com.application.management_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    
    @GetMapping("/api/v1/products/{skuCode}")
    Productdto getProductBySku(@PathVariable("skuCode") String skuCode); 
}