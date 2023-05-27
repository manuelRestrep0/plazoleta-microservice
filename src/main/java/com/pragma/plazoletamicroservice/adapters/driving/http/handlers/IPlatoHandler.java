package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoHabilitacionRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PlatoResponseDto;

import java.util.List;

public interface IPlatoHandler {
    void crearPlato(PlatoRequestDto platoRequestDto);
    void modificarPlato(ModificarPlatoRequestDto modificarPlatoRequestDto);
    void habilitacionPlato(PlatoHabilitacionRequestDto platoHabilitacionRequestDto);
    List<List<PlatoResponseDto>> obtenerPlatos(String nombre, Long id, int elementos);
}
