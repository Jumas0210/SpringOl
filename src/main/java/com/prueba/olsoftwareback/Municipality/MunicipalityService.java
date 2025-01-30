/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.olsoftwareback.Municipality;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Delatorre
 */
@Service
public class MunicipalityService {
    

    private final WebClient webClient;

    public MunicipalityService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.datos.gov.co/resource/xdk5-pm3f.json").build();
    }

    @Cacheable("municipios") // Opcional: Activa caché en memoria
    public List<Municipality> obtenerMunicipios() {
    return webClient.get()
            .retrieve()
            .bodyToMono(List.class)
            .map(lista -> lista.stream()
                    .map(item -> {
                        Map<String, Object> map = (Map<String, Object>) item;
                        return new Municipality(
                                map.get("municipio") != null ? map.get("municipio").toString() : "Desconocido",
                                map.get("departamento") != null ? map.get("departamento").toString() : "Desconocido"
                        );
                    })
                    .toList()
            )
            .block();

    }
}
