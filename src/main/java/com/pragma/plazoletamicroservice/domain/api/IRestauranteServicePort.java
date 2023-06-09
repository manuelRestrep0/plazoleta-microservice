package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestauranteServicePort {

    void crearRestaurante(Restaurante restaurante);
    List<Page<Restaurante>> obtenerRestaurantes(int elementos);
    boolean registrarEmpleado(Long idEmpleado, Long idPropietario, Long idRestaurante);
}
