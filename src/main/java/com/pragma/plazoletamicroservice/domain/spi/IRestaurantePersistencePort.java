package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantePersistencePort {

    void crearRestaurante(Restaurante restaurante);

    Restaurante obtenerRestaurante(Long id);

    List<Page<Restaurante>> obtenerRestaurantes(int elementos);
}
