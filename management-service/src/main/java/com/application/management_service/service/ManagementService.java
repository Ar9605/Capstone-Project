package com.application.management_service.service;

import org.springframework.stereotype.Service;

import com.application.management_service.client.InventoryClient;
import com.application.management_service.client.ProductClient;
import com.application.management_service.client.Productdto;
import com.application.management_service.dto.ProductAvailabilityResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ManagementService {

    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public ManagementService(ProductClient productClient, InventoryClient inventoryClient) {
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFallback")
    public ProductAvailabilityResponse getCompleteProductDetails(String skuCode) {
        
        Productdto product = productClient.getProductBySku(skuCode);
        Integer stockQuantity = inventoryClient.getStockQuantity(skuCode);
        
        String status = (stockQuantity != null && stockQuantity > 0) ? "IN STOCK" : "OUT OF STOCK";

        ProductAvailabilityResponse response = new ProductAvailabilityResponse();
        response.setSkuCode(product.getSkuCode());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantityAvailable(stockQuantity != null ? stockQuantity : 0);
        response.setStatus(status);

        return response;
    }

    public ProductAvailabilityResponse inventoryFallback(String skuCode, Exception exception) {
        ProductAvailabilityResponse response = new ProductAvailabilityResponse();
        response.setSkuCode(skuCode);
        response.setName("Details Temporarily Unavailable");
        response.setPrice(null); 
        response.setQuantityAvailable(0);
        
        response.setStatus("SYSTEM DOWN: " + exception.getMessage()); 
        
        return response;
    }
}