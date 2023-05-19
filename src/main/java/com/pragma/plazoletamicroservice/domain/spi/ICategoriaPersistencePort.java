package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Categoria;

public interface ICategoriaPersistencePort {

    Categoria obtenerCategoria(Long id);
}
