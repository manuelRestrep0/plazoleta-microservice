package com.pragma.plazoletamicroservice.domain.exceptions;

public class NombreRestauranteMalFormatoException extends RuntimeException{
    public NombreRestauranteMalFormatoException(String message){
        super(message);
    }
}
