package com.pragma.plazoletamicroservice.domain.api;

public interface IFeignServicePort {

    boolean validarPropietario(Long id);
    String obtenerIdPropietarioFromToken(String token);

}
