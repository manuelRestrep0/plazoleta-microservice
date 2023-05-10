package com.pragma.plazoletamicroservice.domain.exceptions;

public class RestauranteNoEncontrado extends RuntimeException{
    public RestauranteNoEncontrado(String message) {
        super(message);
    }
}
