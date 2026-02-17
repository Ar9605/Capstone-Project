package com.application.inventory_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.application.inventory_service.dto.Inventorydto;
import com.application.inventory_service.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addInventory(@Valid @RequestBody Inventorydto inventoryDto) {
        inventoryService.addInventory(inventoryDto);
    }

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("skuCode") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/quantity/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public Integer getStockQuantity(@PathVariable("skuCode") String skuCode) {
        return inventoryService.getQuantity(skuCode);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update Inventory", description = "Overwrites the current stock quantity for an existing SKU")
    public void updateInventory(@Valid @RequestBody Inventorydto inventoryDto) {
        inventoryService.updateInventory(inventoryDto);
    }
}