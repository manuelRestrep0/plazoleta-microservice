package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.RestauranteResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestauranteHandler {

    void crearRestaurante(RestauranteRequestDto restauranteRequestDto);

    List<Page<RestauranteResponseDto>> obtenerRestaurantes(int elementos);
}
