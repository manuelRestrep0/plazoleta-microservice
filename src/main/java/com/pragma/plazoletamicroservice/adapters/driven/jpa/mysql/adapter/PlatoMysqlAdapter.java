package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlatoMysqlAdapter implements IPlatoPersistencePort {
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public void crearPlato(Plato plato) {
        platoRepository.save(platoEntityMapper.toEntity(plato));
    }

    @Override
    public void modificarPlato(Plato plato) {
        platoRepository.save(platoEntityMapper.toEntity(plato));
    }
}
