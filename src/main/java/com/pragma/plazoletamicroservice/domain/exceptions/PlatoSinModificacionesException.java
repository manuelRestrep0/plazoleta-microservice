package com.pragma.plazoletamicroservice.domain.exceptions;

public class PlatoSinModificacionesException extends RuntimeException{
    public PlatoSinModificacionesException(String message) {
        super(message);
    }
}
