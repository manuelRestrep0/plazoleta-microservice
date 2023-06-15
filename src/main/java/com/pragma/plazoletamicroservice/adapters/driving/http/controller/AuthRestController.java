package com.pragma.plazoletamicroservice.adapters.driving.http.controller;

import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AuthRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final UsuarioFeignClient usuarioFeignClient;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return usuarioFeignClient.login(authRequestDto);
    }

}
