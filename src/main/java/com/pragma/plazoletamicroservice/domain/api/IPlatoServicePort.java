package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.Plato;

import java.util.List;

public interface IPlatoServicePort {
    void crearPlato(Plato plato);
    void modificarPlato(Long id, String precio, String descripcion);
    void habilitacionPlato(Long id, Boolean estado);
    List<List<Plato>> obtenerPlatos(String nombre, Long id, int elementos);
}
