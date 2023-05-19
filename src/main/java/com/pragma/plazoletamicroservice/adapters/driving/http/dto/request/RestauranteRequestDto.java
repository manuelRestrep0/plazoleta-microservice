package com.pragma.plazoletamicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestauranteRequestDto {

    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9]*)[a-zA-Z0-9\\ ]+$", message = "El nombre no puede ser solo numerico.")
    private String nombre;
    @NotBlank
    private String direccion;
    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3})?((\\d{1,3})|\\d{1,3})\\d{3,4}\\d{4}$", message = "El numero de telefono debe tener entre 6 y 13 carecteres numerico o un '+' al inicio")
    @Size(min = 6, max = 13)
    private String telefono;
    @NotBlank
    @URL
    private String urlLogo;
    @NotBlank
    @Positive
    @Pattern(regexp = "^[0-9]+$", message = "El nit debe ser un numero positivo diferente de cero.")
    private String nit;
}
