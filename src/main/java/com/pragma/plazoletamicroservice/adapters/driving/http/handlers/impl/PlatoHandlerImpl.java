package com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoHabilitacionRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPlatoHandler;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPlatoRequestDtoMapper;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandlerImpl implements IPlatoHandler {
    private final IPlatoServicePort platoServicePort;
    private final IPlatoRequestDtoMapper platoRequestDtoMapper;

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
}
