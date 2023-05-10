package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;

public interface IRestauranteHandler {

    void crearRestaurante(RestauranteRequestDto restauranteRequestDto);
}
