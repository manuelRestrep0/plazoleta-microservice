package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PlatoEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.exceptions.PlatoNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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

    @Override
    public Plato obtenerPlato(Long id) {
        Optional<PlatoEntity> platoEntity = platoRepository.findById(id);
        if(platoEntity.isEmpty()){
            throw new PlatoNoEncontradoException(Constants.PLATO_NO_REGISTRADO);
        }
        return platoEntityMapper.toPlato(platoEntity.get());
    }
}
