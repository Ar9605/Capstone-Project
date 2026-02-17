package com.application.inventory_service.service;

import org.springframework.stereotype.Service;

import com.application.inventory_service.dto.Inventorydto;
import com.application.inventory_service.model.Inventory;
import com.application.inventory_service.repository.InventoryRepository;
import org.springframework.transaction.annotation.Transactional;
import com.application.inventory_service.exception.ResourceNotFoundException;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void addInventory(Inventorydto inventoryDto) {
        Inventory inventory = inventoryRepository.findBySkuCode(inventoryDto.getSkuCode())
                .orElse(new Inventory(inventoryDto.getSkuCode(), 0)); 
        
        inventory.setQuantity(inventory.getQuantity() + inventoryDto.getQuantity());
        inventoryRepository.save(inventory);
    }

    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(inv -> inv.getQuantity() > 0)
                .orElse(false);
    }

    public Integer getQuantity(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(Inventory::getQuantity) 
                .orElse(0);                
    }

    @Transactional
    public void updateInventory(Inventorydto inventoryDto) {
        Inventory existingInventory = inventoryRepository.findBySkuCode(inventoryDto.getSkuCode())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for SKU: " + inventoryDto.getSkuCode()));
        
        existingInventory.setQuantity(inventoryDto.getQuantity());
        
        inventoryRepository.save(existingInventory);
    }
}