/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Merchants;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Delatorre
 */
@Repository
public interface  MerchantsRepository extends JpaRepository<Merchants, Long>{
    
    
    // Buscar por ID (Spring JPA lo hace automático)
    Optional<Merchants> findById(Long id);

    // Buscar por ID con estado específico (Activo/Inactivo)
    Optional<Merchants> findByIdAndEstado(Long id, String estado);

    // Consulta paginada con filtros opcionales
    Page<Merchants> findByNombreRazonSocialContainingIgnoreCaseAndEstadoContainingIgnoreCase(
            String nombreRazonSocial, String estado, Pageable pageable);
    
    @Query("SELECT u.id FROM User u WHERE u.correoElectronico = :correo")
    Long obtenerIdUsuarioPorCorreo(@Param("correo") String correo);

    // Actualizar solo el estado del comerciante con PATCH
    @Modifying
    @Transactional
    @Query("UPDATE Merchants c SET c.estado = :estado WHERE c.id = :id")
    void actualizarEstado(@Param("id") Long id, @Param("estado") String estado);
    
}
