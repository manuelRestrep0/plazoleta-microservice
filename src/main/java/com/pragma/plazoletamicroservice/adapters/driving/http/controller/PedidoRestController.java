package com.pragma.plazoletamicroservice.adapters.driving.http.controller;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PedidosFiltradosRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPedidoHandler;
import com.pragma.plazoletamicroservice.configuration.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoRestController {
    private final IPedidoHandler pedidoHandler;

    @PostMapping("/generar-pedido")
    public ResponseEntity<Map<String,String>> generarPedido(@Valid @RequestBody PedidoRequestDto pedidoRequestDto){
        pedidoHandler.generarPedido(pedidoRequestDto.getIdRestaurante(),pedidoRequestDto.getPlatos());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PEDIDO_CREADO)
        );
    }
    @GetMapping("/obtener-pedidos")
    public List<List<PedidoResponseDto>> obtenerPedidos(@RequestBody PedidosFiltradosRequestDto pedidosFiltradosRequestDto){
        return pedidoHandler.obtenerPedidosPorEstado(pedidosFiltradosRequestDto.getIdRestaurante(), pedidosFiltradosRequestDto.getEstado(), pedidosFiltradosRequestDto.getElementos());
    }
}
