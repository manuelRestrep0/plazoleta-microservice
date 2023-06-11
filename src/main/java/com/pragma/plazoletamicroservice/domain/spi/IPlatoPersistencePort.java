package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Plato;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    Optional<Plato> obtenerPlato(Long id);
    Page<Plato> obtenerPlatos(String nombre, Long id, int elementos, int pagina);
    Boolean verificarRestaurantePlato(Long idRestaurante,Long idPlato);
}
