package com.pragma.plazoletamicroservice;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteServiceTest {
    RestauranteRequestDto restauranteRequestDto;
    static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setUpRestauranteRequestDto(){
        restauranteRequestDto = new RestauranteRequestDto(
          "Las delicias de mi tia",
          "carrera20a",
          "3024261812",
          "https://stackoverflow.com/questions/55020389/spring-feign-client-exception-handling",
                "1234444",
                1L
        );
    }

    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("Cuando el telefono no tiene el formato correcto, se detecta" +
            "que no pasa las validaciones y se verifica que el campo que no paso " +
            "la validacion sea el de telefono")
    @ValueSource(strings = {"","302","+57","3333333333333333333","-2","abc","3.2","302a","3024261812+","302+4261812"})
    void crearRestauranteTelefonoMalFormato(String telefono){
        restauranteRequestDto.setTelefono(telefono);
        Set<ConstraintViolation<RestauranteRequestDto>> violations = validator.validate(restauranteRequestDto);
        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<RestauranteRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("telefono",violation.getPropertyPath().toString());
        }
    }

    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("Cuando el nit no tiene el formato correcto, " +
            "no pasa las validaciones y se verifica que el campo que no paso " +
            "sea el del nit.")
    @ValueSource(strings = {"12a","-2","abc","1.2","09+09"})
    void crearRestauranteNitMalFormato(String nit){
        restauranteRequestDto.setNit(nit);
        Set<ConstraintViolation<RestauranteRequestDto>> violations = validator.validate(restauranteRequestDto);

        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<RestauranteRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("nit",violation.getPropertyPath().toString());
        }
    }
    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("Cuando se ingresa un nombre de solo numeros, no pasa las validaciones" +
            "y se verifica que el campo que no paso sea el del nombre del restaurante")
    @ValueSource(strings = {"12","-2","1.2","09+09","0"})
    void crearRestauranteMalNombre(String nombre){
        restauranteRequestDto.setNombre(nombre);
        Set<ConstraintViolation<RestauranteRequestDto>> violations = validator.validate(restauranteRequestDto);

        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<RestauranteRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("nombre",violation.getPropertyPath().toString());
        }
    }


}
