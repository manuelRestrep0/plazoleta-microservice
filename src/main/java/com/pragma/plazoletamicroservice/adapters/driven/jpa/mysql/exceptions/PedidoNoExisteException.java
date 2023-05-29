package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.exceptions;

public class PedidoNoExisteException extends RuntimeException{

    public PedidoNoExisteException(String message) {
        super(message);
    }
}
