package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDto {

    private Long idRestaurante;
    private List<PlatoPedidoRequestDto> platos;
}
