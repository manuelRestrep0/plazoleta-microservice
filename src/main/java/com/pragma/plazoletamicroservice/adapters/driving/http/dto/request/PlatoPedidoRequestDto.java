package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlatoPedidoRequestDto {
    @NotNull
    @Positive
    private Long idPlato;
    @NotNull
    @Positive
    private Integer cantidad;
}
