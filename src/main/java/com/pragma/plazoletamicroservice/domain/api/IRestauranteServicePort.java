package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.springframework.data.domain.Page;

public interface IRestauranteServicePort {

    void crearRestaurante(Restaurante restaurante);
    Page<Restaurante> obtenerRestaurantes(int elementos, int pagina);
    boolean registrarEmpleado(Long idEmpleado, Long idPropietario, Long idRestaurante);
    boolean validarPropietarioRestaurante(Long idPropietario, Long idRestaurante);
}
