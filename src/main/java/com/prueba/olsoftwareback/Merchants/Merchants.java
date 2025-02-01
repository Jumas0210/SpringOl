/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Merchants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

/**
 *
 * @author Delatorre
 */

@Entity
@Table(name = "COMERCIANTE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Merchants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMERCIANTE")
    private Long id;  

    @NotBlank(message = "El nombre o razón social es obligatorio")
    @Column(name = "NOMBRE_RAZON_SOCIAL", nullable = false)
    private String nombreRazonSocial;

    @NotBlank(message = "El municipio es obligatorio")
    @Column(name = "MUNICIPIO", nullable = false)
    private String municipio;

    @Pattern(regexp = "\\d{7,15}", message = "El teléfono debe contener entre 7 y 15 dígitos")
    @Column(name = "TELEFONO")
    private String telefono;

    @Email(message = "El correo electrónico debe ser válido")
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_REGISTRO", nullable = false, updatable = false)
    private Date fechaRegistro;  

    @NotNull(message = "El estado es obligatorio")
    @Pattern(regexp = "Activo|Inactivo", message = "El estado debe ser 'Activo' o 'Inactivo'")
    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZACION", nullable = false, insertable = false, updatable = false)
    private Date fechaActualizacion;  


    @Column(name = "USUARIO_ACTUALIZACION", nullable = false)
    private Integer usuarioActualizacion;  
}
