package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Plato;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    Plato obtenerPlato(Long id);
}
