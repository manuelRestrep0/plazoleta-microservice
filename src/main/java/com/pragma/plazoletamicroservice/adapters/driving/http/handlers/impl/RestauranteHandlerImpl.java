package com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.RestauranteResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IRestauranteHandler;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IRestauranteRequestMapper;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IRestauranteResponseMapper;
import com.pragma.plazoletamicroservice.domain.api.IRestauranteServicePort;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteHandlerImpl implements IRestauranteHandler {

    private final IRestauranteServicePort restauranteServicePort;
    private final IRestauranteRequestMapper restauranteRequestMapper;
    private final IRestauranteResponseMapper restauranteResponseMapper;
    @Override
    public void crearRestaurante(RestauranteRequestDto restauranteRequestDto) {
        restauranteServicePort.crearRestaurante(restauranteRequestMapper.toRestaurante(restauranteRequestDto));
    }

    @Override
    public List<List<RestauranteResponseDto>> obtenerRestaurantes(int elementos) {
        List<Page<Restaurante>> restaurantes = restauranteServicePort.obtenerRestauranres(elementos);
        List<List<RestauranteResponseDto>> respuesta = new ArrayList<>();
        restaurantes.stream().forEach(restaurantePage -> respuesta.add(restaurantePage.map(restauranteResponseMapper::toResponse).getContent()));
        return respuesta;
    }
}
