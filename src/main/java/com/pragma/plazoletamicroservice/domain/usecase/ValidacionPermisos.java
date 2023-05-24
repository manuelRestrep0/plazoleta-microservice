package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;

public class ValidacionPermisos {
    private ValidacionPermisos() {
        throw new IllegalStateException("Utility class");
    }
    public static void validarRol(String rolUsuario, String rolAutorizado){
        if(!rolUsuario.equals(rolAutorizado)){
            throw new UsuarioNoAutorizadoException(Constants.USUARIO_NO_AUTORIZADO);
        }
    }

}
