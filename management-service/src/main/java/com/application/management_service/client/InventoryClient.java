package com.application.management_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    
    @GetMapping("/api/v1/inventory/quantity/{skuCode}")
    Integer getStockQuantity(@PathVariable("skuCode") String skuCode);
}