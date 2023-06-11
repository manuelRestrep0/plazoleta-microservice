package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.Plato;
import org.springframework.data.domain.Page;

public interface IPlatoServicePort {
    void crearPlato(Plato plato);
    void modificarPlato(Long id, String precio, String descripcion);
    void habilitacionPlato(Long id, Boolean estado);
    Page<Plato> obtenerPlatos(String nombre, Long id, int elementos, int numeroPagina);
}
