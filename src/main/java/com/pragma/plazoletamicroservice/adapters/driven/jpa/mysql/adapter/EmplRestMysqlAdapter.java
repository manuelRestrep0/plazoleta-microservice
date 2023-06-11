package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.EmplRestEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IEmplRestRepository;
import com.pragma.plazoletamicroservice.domain.spi.IEmplRestPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    public List<Long> listaEmpleadosFromRestaurante(Long idRestaurante) {
        List<EmplRestEntity> emplRestEntities = emplRestRepository.findAllByIdRestaurante(idRestaurante);
        List<Long> empleados = new ArrayList<>();
        emplRestEntities.forEach(emplRestEntity -> empleados.add(emplRestEntity.getIdEmpleado()));
        return empleados;
    }

    @Override
    public Boolean validarExistenciaEmpleadoRestaurante(Long idEmpleado, Long idRestaurante) {
        return emplRestRepository.findByIdEmpleadoAndIdRestaurante(idEmpleado,idRestaurante);
    }
}
