package com.pragma.plazoletamicroservice.domain.exceptions;

public class PedidoPlatoDiferenteRestauranteException extends RuntimeException{
    public PedidoPlatoDiferenteRestauranteException(String message) {
        super(message);
    }
}
