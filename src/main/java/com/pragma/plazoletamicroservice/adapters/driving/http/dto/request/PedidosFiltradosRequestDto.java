package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PedidosFiltradosRequestDto {
    @NotNull
    @Positive
    private Long idRestaurante;
    @NotBlank
    private String estado;
    @NotNull
    @Positive
    private int elementos;
}
