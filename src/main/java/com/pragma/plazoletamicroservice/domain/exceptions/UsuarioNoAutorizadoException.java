package com.pragma.plazoletamicroservice.domain.exceptions;

public class UsuarioNoAutorizadoException extends RuntimeException{
    public UsuarioNoAutorizadoException(String message) {
        super(message);
    }
}
