package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;

public interface IPlatoHandler {
    void crearPlato(PlatoRequestDto platoRequestDto);
}
