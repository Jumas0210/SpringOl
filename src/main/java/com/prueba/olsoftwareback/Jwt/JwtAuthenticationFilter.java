/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Delatorre
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        final String token= getTokenFormRequest(request);
        final String correoElectronico;
        final String rol;
        final Integer id;
        
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }
        
        correoElectronico=jwtService.getUsernameFromToken(token);
        rol = jwtService.getClaim(token, claims -> claims.get("rol", String.class));
        id = jwtService.getClaim(token, claims -> claims.get("id", Integer.class));
        
        if (correoElectronico!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=userDetailsService.loadUserByUsername(correoElectronico);

            if (jwtService.isTokenValid(token, userDetails))
            {
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(rol));
                
                System.out.println("Rol obtenido del token: " + rol);
                System.out.println("Autoridades asignadas: " + authorities);

                
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities 
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                
                System.out.println("Rol obtenido del token: " + rol);
                System.out.println("id obtenido del token: " + id);
                System.out.println("Autoridades asignadas: " + authorities);
                System.out.println("Usuario autenticado: " + userDetails.getUsername());
                System.out.println("Autoridades en SecurityContext: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }

        }
        
        filterChain.doFilter(request, response);
    }
    
    private String getTokenFormRequest(HttpServletRequest request){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")){
            
            return authHeader.substring(7);
        }
        
        return null;
    }
    
}
