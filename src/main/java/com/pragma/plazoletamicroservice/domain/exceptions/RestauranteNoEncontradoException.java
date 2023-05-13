package com.pragma.plazoletamicroservice.domain.exceptions;

public class RestauranteNoEncontradoException extends RuntimeException{
    public RestauranteNoEncontradoException(String message) {
        super(message);
    }
}
