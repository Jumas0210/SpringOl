/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.User;

import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @author Delatorre
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROL")
public class Rol {
    
    @Id
    @Column(name = "ID_ROL", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 50)
    private String nombre; 
}
