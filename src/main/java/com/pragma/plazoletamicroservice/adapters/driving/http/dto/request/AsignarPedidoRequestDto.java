package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AsignarPedidoRequestDto {

    Long idRestaurante;
    List<Long> pedidos;
}
