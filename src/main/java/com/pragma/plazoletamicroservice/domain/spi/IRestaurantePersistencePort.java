package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Restaurante;

public interface IRestaurantePersistencePort {

    void crearRestaurante(Restaurante restaurante);

    Restaurante obtenerRestaurante(Long id);
}
