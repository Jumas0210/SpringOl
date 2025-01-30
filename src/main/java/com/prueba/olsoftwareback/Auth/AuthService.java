/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Auth;

import com.prueba.olsoftwareback.Jwt.JwtService;
import com.prueba.olsoftwareback.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Delatorre
 */

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse login(LoginRequest request){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreoElectronico(),request.getContrasena()));
       UserDetails user = userRepository.findByCorreoElectronico(request.getCorreoElectronico()).orElseThrow();
       String token = jwtService.getToken(user);
       return AuthResponse.builder()
               .token(token)
               .build();
    }
    
}
