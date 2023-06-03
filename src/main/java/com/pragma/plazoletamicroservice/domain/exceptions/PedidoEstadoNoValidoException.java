package com.pragma.plazoletamicroservice.domain.exceptions;

public class PedidoEstadoNoValidoException extends RuntimeException{
    public PedidoEstadoNoValidoException(String message) {
        super(message);
    }
}
