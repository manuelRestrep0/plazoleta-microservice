package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModificarPlatoRequestDto {

    @NotNull
    private Long id;
    @Positive
    @Pattern(regexp = "^[0-9]+$", message = "El precio solo debe contener numeros")
    private String precio;
    private String descripcion;
}
