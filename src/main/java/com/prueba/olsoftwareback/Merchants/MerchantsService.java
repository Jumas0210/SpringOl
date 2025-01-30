/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Merchants;

import com.prueba.olsoftwareback.Jwt.JwtService;
import com.prueba.olsoftwareback.Merchants.Merchants;
import com.prueba.olsoftwareback.Merchants.MerchantsRepository;
import com.prueba.olsoftwareback.User.User;
import com.prueba.olsoftwareback.User.UserRepository;
import com.prueba.olsoftwareback.Utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;

import java.util.Optional;

/**
 *
 * @author Delatorre
 */
@Service
@RequiredArgsConstructor
public class MerchantsService {

    private final MerchantsRepository merchantsRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ResponseEntity<ApiResponse<Page<Merchants>>> obtenerComerciantes(String nombre, String estado, Pageable pageable) {
        Page<Merchants> comerciantes = merchantsRepository.findByNombreRazonSocialContainingIgnoreCaseAndEstadoContainingIgnoreCase(nombre, estado, pageable);
        return ResponseEntity.ok(new ApiResponse<>("success", "Lista de comerciantes obtenida", comerciantes));
    }

    public ResponseEntity<ApiResponse<Merchants>> obtenerComerciantePorId(Long id) {
        Merchants comerciante = merchantsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el comerciante con ID " + id));
        return ResponseEntity.ok(new ApiResponse<>("success", "Comerciante encontrado", comerciante));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Merchants>> crearComerciante(@Valid Merchants comerciante) {
        System.out.println("🔹 Entrando a crearComerciante()");  // 🔍 LOG PARA DEPURAR
        comerciante.setUsuarioActualizacion(getUserAuthenticated());
        
        Merchants nuevoComerciante = merchantsRepository.save(comerciante);
        return ResponseEntity.ok(new ApiResponse<>("success", "Comerciante creado con éxito", nuevoComerciante));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Merchants>> actualizarComerciante(Long id, @Valid Merchants detallesActualizados) {
        Merchants comercianteExistente = merchantsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el comerciante con ID " + id));

        comercianteExistente.setNombreRazonSocial(detallesActualizados.getNombreRazonSocial());
        comercianteExistente.setMunicipio(detallesActualizados.getMunicipio());
        comercianteExistente.setTelefono(detallesActualizados.getTelefono());
        comercianteExistente.setCorreoElectronico(detallesActualizados.getCorreoElectronico());
        comercianteExistente.setEstado(detallesActualizados.getEstado());

        comercianteExistente.setUsuarioActualizacion(getUserAuthenticated());

        Merchants comercianteActualizado = merchantsRepository.save(comercianteExistente);
        return ResponseEntity.ok(new ApiResponse<>("success", "Comerciante actualizado con éxito", comercianteActualizado));
    }

    @Transactional
    public ResponseEntity<ApiResponse<String>> cambiarEstadoComerciante(Long id, String estado) {
        Merchants comerciante = merchantsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el comerciante con ID " + id));

        comerciante.setEstado(estado);
        comerciante.setUsuarioActualizacion(getUserAuthenticated());

        merchantsRepository.save(comerciante);

        return ResponseEntity.ok(new ApiResponse<>("success", "Estado del comerciante actualizado", "Nuevo estado: " + estado));
    }

    @Transactional
    public ResponseEntity<ApiResponse<String>> eliminarComerciante(Long id) {
        merchantsRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Comerciante eliminado con éxito", null));
    }

    private Integer getUserAuthenticated() {
    System.out.println("✅ Entrando a getUserAuthenticated()");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
        throw new IllegalStateException("No hay usuario autenticado");
    }

    // Verificar si el principal es una instancia de UserDetails (Spring Security)
    if (authentication.getPrincipal() instanceof UserDetails userDetails) {

        // Si usas una clase personalizada `User`, obtén el ID
        if (userDetails instanceof User usuario) {
            Integer userId = usuario.getId();
            System.out.println("🔹 ID Usuario autenticado: " + userId);
            return userId;
        }
    }

    throw new IllegalStateException("No se pudo obtener el usuario autenticado");
}

}
