package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Plato;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    Plato obtenerPlato(Long id);
    List<Page<Plato>> obtenerPlatos(String nombre, Long id, int elementos);
}
