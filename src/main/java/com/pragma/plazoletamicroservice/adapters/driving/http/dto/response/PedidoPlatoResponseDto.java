package com.pragma.plazoletamicroservice.adapters.driving.http.dto.response;

import lombok.Data;

@Data
public class PedidoPlatoResponseDto {
    private PedidoResponseDto idPedido;
    private PlatoResponseDto idPplato;
    private Integer cantidad;
}
