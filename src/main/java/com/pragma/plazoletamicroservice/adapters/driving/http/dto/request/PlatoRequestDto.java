package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class PlatoRequestDto {
    @NotBlank
    private String nombre;
    @NotNull
    private Long idCategoria;
    @NotBlank
    private String descripcion;
    @NotBlank
    @Positive
    @Pattern(regexp = "^[0-9]+$", message = "El precio solo debe contener numeros")
    private String precio;
    @NotNull
    private Long idRestaurante;
    @NotBlank
    private String urlImagen;
}
