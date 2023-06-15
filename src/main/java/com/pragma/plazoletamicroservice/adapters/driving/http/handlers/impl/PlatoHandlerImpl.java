package com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoHabilitacionRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PlatoResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPlatoHandler;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPlatoRequestDtoMapper;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPlatoResponseDtoMapper;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandlerImpl implements IPlatoHandler {
    private final IPlatoServicePort platoServicePort;
    private final IPlatoRequestDtoMapper platoRequestDtoMapper;
    private final IPlatoResponseDtoMapper platoResponseDtoMapper;

    @Override
    public void crearPlato(PlatoRequestDto platoRequestDto) {
        platoServicePort.crearPlato(platoRequestDtoMapper.toPlato(platoRequestDto));
    }

    @Override
    public void modificarPlato(ModificarPlatoRequestDto modificarPlatoRequestDto) {
        platoServicePort.modificarPlato(modificarPlatoRequestDto.getId(), modificarPlatoRequestDto.getPrecio(), modificarPlatoRequestDto.getDescripcion());
    }

    @Override
    public void habilitacionPlato(PlatoHabilitacionRequestDto platoHabilitacionRequestDto) {
        platoServicePort.habilitacionPlato(platoHabilitacionRequestDto.getId(), platoHabilitacionRequestDto.getDisponibilidad());
    }
    @Override
    public Page<PlatoResponseDto> obtenerPlatos(String nombre, Long id, int elementos, int numeroPagina) {
        Page<Plato> platos = platoServicePort.obtenerPlatos(nombre, id, elementos, numeroPagina);
        return platos.map(platoResponseDtoMapper::toResponse);
    }
}
