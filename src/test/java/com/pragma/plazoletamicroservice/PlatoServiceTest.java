package com.pragma.plazoletamicroservice;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlatoServiceTest {

    PlatoRequestDto platoRequestDto;
    static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setUpPlatoRequestDto(){
        platoRequestDto = new PlatoRequestDto(
                "Nombre del plato",
                1L,
                "Descripcion del plato",
                "40000",
                4L,
                "https://twitter.com/home"
        );
    }

    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("Cuando el precio contiene letras o no es un numero positivo diferente de cero, " +
            "no pasa las validaciones y se verifica que el campo que no paso " +
            "sea el del precio.")
    @ValueSource(strings = {"12a","-2","abc","1.2","09+09","0",""})
    void crearPlatoPrecioMalFormato(String precio){
        platoRequestDto.setPrecio(precio);
        Set<ConstraintViolation<PlatoRequestDto>> violations = validator.validate(platoRequestDto);

        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<PlatoRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("precio",violation.getPropertyPath().toString());
        }
    }

}
