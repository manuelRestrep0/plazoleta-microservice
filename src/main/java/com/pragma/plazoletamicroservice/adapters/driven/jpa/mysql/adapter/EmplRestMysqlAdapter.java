package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.EmplRestEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IEmplRestRepository;
import com.pragma.plazoletamicroservice.domain.spi.IEmplRestPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmplRestMysqlAdapter implements IEmplRestPersistencePort
{
    private final IEmplRestRepository emplRestRepository;
    @Override
    public void guardarEmpleadoRestaurante(Long idEmpleado, Long idRestaurante) {
        EmplRestEntity emplRest = new EmplRestEntity();
        emplRest.setIdEmpleado(idEmpleado);
        emplRest.setIdRestaurante(idRestaurante);
        emplRestRepository.save(emplRest);
    }
}
