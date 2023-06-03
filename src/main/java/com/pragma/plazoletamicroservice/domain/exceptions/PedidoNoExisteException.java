package com.pragma.plazoletamicroservice.domain.exceptions;

public class PedidoNoExisteException extends RuntimeException{

    public PedidoNoExisteException(String message) {
        super(message);
    }
}
