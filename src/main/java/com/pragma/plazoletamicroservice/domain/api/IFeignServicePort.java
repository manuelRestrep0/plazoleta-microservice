package com.pragma.plazoletamicroservice.domain.api;

public interface IFeignServicePort {

    boolean validarPropietario(Long id);
    String obtenerIdUsuarioFromToken(String token);
    String obtenerRolFromToken(String token);
    String obtenerCorreoFromUsuario(Long idUsuario);

}
