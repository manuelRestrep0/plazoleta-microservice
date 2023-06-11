package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoHabilitacionRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PlatoResponseDto;
import org.springframework.data.domain.Page;

public interface IPlatoHandler {
    void crearPlato(PlatoRequestDto platoRequestDto);
    void modificarPlato(ModificarPlatoRequestDto modificarPlatoRequestDto);
    void habilitacionPlato(PlatoHabilitacionRequestDto platoHabilitacionRequestDto);
    Page<PlatoResponseDto> obtenerPlatos(String nombre, Long id, int elementos, int numeroPagina);
}
