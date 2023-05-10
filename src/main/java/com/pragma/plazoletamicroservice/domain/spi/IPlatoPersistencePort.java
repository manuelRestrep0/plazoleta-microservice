package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Plato;

public interface IPlatoPersistencePort {
    void crearPlato(Plato plato);
    void modificarPlato(Plato plato);
}
