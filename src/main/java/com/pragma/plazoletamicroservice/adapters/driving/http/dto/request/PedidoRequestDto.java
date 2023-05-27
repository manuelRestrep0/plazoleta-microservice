package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Data
public class PedidoRequestDto {

    @NotNull
    @Positive
    private Long idRestaurante;
    @UniqueElements
    @Valid
    private List<PlatoPedidoRequestDto> platos;
}
