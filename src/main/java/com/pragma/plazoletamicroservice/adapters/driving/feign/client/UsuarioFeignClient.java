package com.pragma.plazoletamicroservice.adapters.driving.feign.client;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AuthRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import com.pragma.plazoletamicroservice.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USUARIO-MICROSERVICE-API", url = "http://localhost:8080", configuration = FeignClientConfig.class)
public interface UsuarioFeignClient {

    @GetMapping(value = "/usuario/validar-propietario/{id}")
    Boolean validarPropietario(@PathVariable("id") Long id);

    @GetMapping(value = "/auth/obtener-id/{token}")
    String idUsuario(@PathVariable("token") String token);
    @GetMapping(value = "/auth/obtener-rol/{token}")
    String rolUsuario(@PathVariable("token") String token);

    @PostMapping(value = "/auth/login")
    ResponseEntity<JwtResponseDto> login(@RequestBody AuthRequestDto authRequestDto);
}
