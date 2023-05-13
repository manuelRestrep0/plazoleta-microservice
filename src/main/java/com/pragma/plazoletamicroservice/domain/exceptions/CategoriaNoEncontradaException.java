package com.pragma.plazoletamicroservice.domain.exceptions;

public class CategoriaNoEncontradaException extends RuntimeException{
    public CategoriaNoEncontradaException(String message) {
        super(message);
    }
}
