package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IRestaurantePersistencePort {

    void crearRestaurante(Restaurante restaurante);
    Optional<Restaurante> obtenerRestaurante(Long id);
    Page<Restaurante> obtenerRestaurantes(int elementos, int numeroPagina);
    Boolean validarExistenciaRestaurante(String nit);
    Boolean validarExistenciaRestaurante(Long idRestaurante, Long idPropietario);
}
