package com.application.management_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.application.management_service.dto.ProductAvailabilityResponse;
import com.application.management_service.service.ManagementService;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/product-details/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public ProductAvailabilityResponse getCompleteProductDetails(@PathVariable("skuCode") String skuCode) {
        return managementService.getCompleteProductDetails(skuCode);
    }
}