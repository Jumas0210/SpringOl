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
    public ResponseEntity<ApiResponse<Page<Merchants>>> obtenerComerciantes(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "") String estado,
            Pageable pageable) {
        return merchantsService.obtenerComerciantes(nombre, estado, pageable);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Merchants>> obtenerComerciantePorId(@PathVariable Long id) {
        return merchantsService.obtenerComerciantePorId(id);
    }
    
     @PostMapping
    public ResponseEntity<ApiResponse<Merchants>> crearComerciante(@Valid @RequestBody Merchants comerciante) {
         System.out.println("✅ Petición recibida en MerchantsController");
        return merchantsService.crearComerciante(comerciante);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Merchants>> actualizarComerciante(
            @PathVariable Long id,
            @Valid @RequestBody Merchants comerciante) {
        return merchantsService.actualizarComerciante(id, comerciante);
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<String>> cambiarEstadoComerciante(
            @PathVariable Long id,
            @RequestParam String estado) {
        return merchantsService.cambiarEstadoComerciante(id, estado);
    }
    
    @DeleteMapping("/{id}")    
    public ResponseEntity<ApiResponse<String>> eliminarComerciante(@PathVariable Long id) {
        return merchantsService.eliminarComerciante(id);
    }
    
}
