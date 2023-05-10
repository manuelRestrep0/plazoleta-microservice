package com.pragma.plazoletamicroservice.domain.usecase.validaciones;

import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.exceptions.NombreRestauranteMalFormatoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesRestaurante {

    protected Pattern patron;
    protected Matcher matcher;
    protected String expresionRegular;
    protected boolean respuestaValidacion;

    public void validarNombre(String nombreRestaurante){
        expresionRegular = "(?=.*[a-zA-Z])(?=.*[0-9]*)[a-zA-Z0-9\\ ]+$";
        respuestaValidacion = ejecutarMatcher(nombreRestaurante);
        if(!respuestaValidacion){
            throw new NombreRestauranteMalFormatoException(Constants.NOMBRE_RESTAURANTE_MAL_FORMATO);
        }

    }
    protected boolean ejecutarMatcher(String validar){
        patron = Pattern.compile(expresionRegular);
        matcher = patron.matcher(validar);
        return matcher.find();
    }
}
