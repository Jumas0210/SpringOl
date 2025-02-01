/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Merchants;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prueba.olsoftwareback.Utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
/**
 *
 * @author Delatorre
 */
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantsController {

    private final MerchantsService merchantsService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Merchants>>> getMerchants(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "") String estado,
            Pageable pageable) {
        return merchantsService.getMerchants(nombre, estado, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Merchants>> getMerchantById(@PathVariable Long id) {
        return merchantsService.getMerchantById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Merchants>> createMerchant(@Valid @RequestBody Merchants merchant) {
        return merchantsService.createMerchant(merchant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Merchants>> updateMerchant(
            @PathVariable Long id,
            @Valid @RequestBody Merchants merchant) {
        return merchantsService.updateMerchant(id, merchant);
    }

    @PatchMapping("/{id}/toggle-state")
    public ResponseEntity<ApiResponse<String>> toggleStateMerchant(@PathVariable Long id) {
        return merchantsService.toggleStateMerchant(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMerchant(@PathVariable Long id) {
        return merchantsService.deleteMerchant(id);
    }

}
