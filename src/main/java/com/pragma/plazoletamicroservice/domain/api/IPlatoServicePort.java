package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.Plato;

public interface IPlatoServicePort {
    void crearPlato(Plato plato);
    void modificarPlato(Long id, String precio, String descripcion);
}
