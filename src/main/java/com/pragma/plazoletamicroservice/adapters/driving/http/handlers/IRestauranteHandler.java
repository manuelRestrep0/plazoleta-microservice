package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.RestauranteResponseDto;

import java.util.List;

public interface IRestauranteHandler {

    void crearRestaurante(RestauranteRequestDto restauranteRequestDto);
    List<List<RestauranteResponseDto>> obtenerRestaurantes(int elementos);
    boolean registrarEmpleado(Long idEmpleado, Long idPropietario, Long idRestaurante);
}
