package com.pragma.plazoletamicroservice.domain.exceptions;

public class CodigoIncorrectoException extends RuntimeException{
    public CodigoIncorrectoException(String message) {
        super(message);
    }
}
