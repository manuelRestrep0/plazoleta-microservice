package com.pragma.plazoletamicroservice.adapter.restaurante;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = RestauranteRequestDto.class)
@SpringBootTest
class RestauranteRequestDtoTest {
    static Validator validator;
    RestauranteRequestDto restauranteRequestDto;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @BeforeEach
    public void setRestauranteRequestDto(){
        restauranteRequestDto = new RestauranteRequestDto(
                "KFC",
                "local80",
                "3024573812",
                "https://twitter.com/home",
                "100111",
                1L
        );
    }
    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("El nombre puede tener numero pero no ser solo numeros.")
    @ValueSource(strings = {"-2","1.2","09+09","0",""})
    void restauranteNombresNoValidos(String nombre){
        restauranteRequestDto.setNombre(nombre);

        Set<ConstraintViolation<RestauranteRequestDto>> violations = validator.validate(restauranteRequestDto);

        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<RestauranteRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("nombre",violation.getPropertyPath().toString());
        }
    }
    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("El numero puede contener un + al inicio y debe tener entre 6 y 13 caracteres")
    @ValueSource(strings = {"12a","-2","abc","1.2","09+09","0",""})
    void restauranteTelefonosNoValidos(String telefono){
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
    @DisplayName("El url ingresado debe ser un String con el formato correcto de un url.")
    @ValueSource(strings = {"www.algo.","htsct.hola.com","abc","","123456.7895"})
    void restauranteUrlLogoNoValidos(String url){
        restauranteRequestDto.setUrlLogo(url);

        Set<ConstraintViolation<RestauranteRequestDto>> violations = validator.validate(restauranteRequestDto);

        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<RestauranteRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("urlLogo",violation.getPropertyPath().toString());
        }
    }
    @ParameterizedTest(name = "Valor: {0} ")
    @DisplayName("El nit debe contener solo numeros.")
    @ValueSource(strings = {"12a","-2","abc","1.2","09+09","0",""})
    void restauranteNitNoValidos(String nit){
        restauranteRequestDto.setNit(nit);

        Set<ConstraintViolation<RestauranteRequestDto>> violations = validator.validate(restauranteRequestDto);

        assertEquals(true, !violations.isEmpty());
        for (ConstraintViolation<RestauranteRequestDto> violation: violations
        ) {
            violation = violations.iterator().next();
            assertEquals("nit",violation.getPropertyPath().toString());
        }
    }
}
