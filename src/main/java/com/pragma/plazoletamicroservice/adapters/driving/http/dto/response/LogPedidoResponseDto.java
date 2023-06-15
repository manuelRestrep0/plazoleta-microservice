package com.pragma.plazoletamicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LogPedidoResponseDto {
    private Long idPedido;
    private LocalDateTime fecha;
    private String estadoAnterior;
    private String estadoNuevo;
}
