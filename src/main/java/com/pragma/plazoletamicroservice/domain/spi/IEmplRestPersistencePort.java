package com.pragma.plazoletamicroservice.domain.spi;

public interface IEmplRestPersistencePort {

    void guardarEmpleadoRestaurante(Long idEmpleado, Long idRestaurante);
}
