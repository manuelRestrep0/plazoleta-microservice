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
    public Page<RestauranteResponseDto> obtenerRestaurantes(int elementos, int pagina) {
        Page<Restaurante> restaurantes = restauranteServicePort.obtenerRestaurantes(elementos, pagina);
        return restaurantes.map(restauranteResponseMapper::toResponse);
    }
    @Override
    public boolean registrarEmpleado(Long idEmpleado, Long idPropietario, Long idRestaurante) {
        return restauranteServicePort.registrarEmpleado(idEmpleado,idPropietario,idRestaurante);
    }
    @Override
    public boolean validarPropietarioRestaurante(Long idPropieratio, Long idRestaurante) {
        return restauranteServicePort.validarPropietarioRestaurante(idPropieratio, idRestaurante);
    }
}
