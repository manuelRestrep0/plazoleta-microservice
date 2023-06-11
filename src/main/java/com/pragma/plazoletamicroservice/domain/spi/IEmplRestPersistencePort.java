package com.pragma.plazoletamicroservice.domain.spi;

import java.util.List;

public interface IEmplRestPersistencePort {

    void guardarEmpleadoRestaurante(Long idEmpleado, Long idRestaurante);
    List<Long> listaEmpleadosFromRestaurante(Long idRestaurante);
    Boolean validarExistenciaEmpleadoRestaurante(Long idEmpleado, Long idRestaurante);
}
