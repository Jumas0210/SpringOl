/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Municipality;

import com.prueba.olsoftwareback.Utils.ApiResponse;
import com.prueba.olsoftwareback.Municipality.Municipality;
import com.prueba.olsoftwareback.Municipality.MunicipalityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
        

/**
 *
 * @author Delatorre
 */
@RestController
@RequestMapping("/api/v1/municipalities")
@RequiredArgsConstructor
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    @GetMapping
    @PreAuthorize("isAuthenticated()") // Solo usuarios autenticados pueden acceder
    public ResponseEntity<ApiResponse<List<Municipality>>> obtenerMunicipios() {
        List<Municipality> municipios = municipalityService.obtenerMunicipios();
        return ResponseEntity.ok(new ApiResponse<>("success", "Lista de municipios obtenida con éxito", municipios));
    }
}
