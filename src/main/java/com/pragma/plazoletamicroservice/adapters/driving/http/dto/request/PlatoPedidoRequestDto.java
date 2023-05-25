package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import lombok.Data;

@Data
public class PlatoPedidoRequestDto {
    private Long idPlato;
    private Integer cantidad;
}
