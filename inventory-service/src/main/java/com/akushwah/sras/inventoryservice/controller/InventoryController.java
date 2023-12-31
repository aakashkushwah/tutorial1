package com.akushwah.sras.inventoryservice.controller;

import com.akushwah.sras.inventoryservice.dto.InventoryResponse;
import com.akushwah.sras.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("sku-code") List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
