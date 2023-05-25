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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<List<PlatoResponseDto>> obtenerPlatos(String nombre, Long id, int elementos) {
        List<List<Plato>> platos = platoServicePort.obtenerPlatos(nombre, id, elementos);
        List<List<PlatoResponseDto>> respuesta = new ArrayList<>();
        platos.forEach(page -> respuesta.add(platoResponseDtoMapper.toListResponse(page)));
        return respuesta;
    }
}
