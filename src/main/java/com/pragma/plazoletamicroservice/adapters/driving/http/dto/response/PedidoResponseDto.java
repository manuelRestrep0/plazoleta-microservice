package com.pragma.plazoletamicroservice.adapters.driving.http.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoResponseDto {
    private Long idCliente;
    private LocalDate fecha;
    private String estado;
    private String restaurante;
}
