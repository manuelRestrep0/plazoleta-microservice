package com.pragma.plazoletamicroservice.domain.exceptions;

public class UsuarioNoPropietarioException extends RuntimeException{
    public UsuarioNoPropietarioException(String message) {
        super(message);
    }
}
