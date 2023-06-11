package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.RestauranteResponseDto;
import org.springframework.data.domain.Page;


public interface IRestauranteHandler {

    void crearRestaurante(RestauranteRequestDto restauranteRequestDto);
    Page<RestauranteResponseDto> obtenerRestaurantes(int elementos, int pagina);
    boolean registrarEmpleado(Long idEmpleado, Long idPropietario, Long idRestaurante);
    boolean validarPropietarioRestaurante(Long idPropieratio,Long idRestaurante);
}
