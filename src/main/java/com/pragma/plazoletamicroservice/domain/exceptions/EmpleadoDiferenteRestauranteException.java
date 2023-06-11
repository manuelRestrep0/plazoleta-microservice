package com.pragma.plazoletamicroservice.domain.exceptions;

public class EmpleadoDiferenteRestauranteException extends RuntimeException{
    public EmpleadoDiferenteRestauranteException(String message) {
        super(message);
    }
}
