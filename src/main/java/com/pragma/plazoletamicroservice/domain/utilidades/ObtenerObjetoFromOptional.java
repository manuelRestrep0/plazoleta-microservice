package com.pragma.plazoletamicroservice.domain.utilidades;

import com.pragma.plazoletamicroservice.domain.exceptions.PlatoNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;

import java.util.Optional;

public class ObtenerObjetoFromOptional {
    private ObtenerObjetoFromOptional() {
        throw new IllegalStateException("Utility class");
    }
    public static Restaurante obtenerRestaurante(Optional<Restaurante> restaurante){
        if (restaurante.isEmpty()){
            throw new RestauranteNoEncontradoException(Constantes.RESTAURANTE_NO_ENCONTRADO);
        }
        return restaurante.get();
    }
    public static Plato obtenerPlato(Optional<Plato> plato){
        if(plato.isEmpty()){
            throw new PlatoNoEncontradoException(Constantes.PLATO_NO_REGISTRADO);
        }
        return plato.get();
    }
}
