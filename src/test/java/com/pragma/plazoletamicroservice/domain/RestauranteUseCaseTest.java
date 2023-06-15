package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.NitYaRegistradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IEmplRestPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.RestauranteUseCase;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = RestauranteUseCase.class)
@SpringBootTest
class RestauranteUseCaseTest {

    @MockBean
    IRestaurantePersistencePort restaurantePersistencePort;
    @MockBean
    IEmplRestPersistencePort emplRestPersistencePort;
    @MockBean
    IFeignServicePort feignServicePort;
    @InjectMocks
    @Autowired
    RestauranteUseCase restauranteUseCase;
    Restaurante restaurante;

    @BeforeEach
    void setRestaurante(){
        restaurante = new Restaurante(
                1L,
                "kfc",
                "123",
                "local80",
                "3024261712",
                "https://twitter.com/home",
                2L
        );
    }
    @Test
    void crearRestauranteCorrectamente(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("3");
        when(restaurantePersistencePort.validarExistenciaRestaurante(any())).thenReturn(false);
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(false);
        restaurante = new Restaurante(
                1L,
                "kfc",
                "123",
                "local80",
                "3024261712",
                "https://twitter.com/home",
                null
        );

        restauranteUseCase.crearRestaurante(restaurante);

        assertEquals(3L,restaurante.getIdPropietario());
    }
    @Test
    void crearRestauranteRolNoAutorizado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("3");
        when(restaurantePersistencePort.validarExistenciaRestaurante(any())).thenReturn(false);
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(false);
        restaurante = new Restaurante(
                1L,
                "kfc",
                "123",
                "local80",
                "3024261712",
                "https://twitter.com/home",
                null
        );

        assertThrows(UsuarioNoAutorizadoException.class, () ->restauranteUseCase.crearRestaurante(restaurante));
    }
    @Test
    void crearRestauranteNitRegistrado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("3");
        when(restaurantePersistencePort.validarExistenciaRestaurante(any())).thenReturn(true);
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(false);
        restaurante = new Restaurante(
                1L,
                "kfc",
                "123",
                "local80",
                "3024261712",
                "https://twitter.com/home",
                null
        );

        assertThrows(NitYaRegistradoException.class, ()-> restauranteUseCase.crearRestaurante(restaurante));
    }
    @Test
    void registrarEmpleadoCasoExitoso(){
        Long idEmpleado = 1L;
        Long idPropietario = 2L;
        Long idRestaurante = 3L;
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(true);

        boolean respuesta = restauranteUseCase.registrarEmpleado(idEmpleado,idPropietario,idRestaurante);

        assertTrue(respuesta);
    }
    @Test
    void registrarEmpleadoCasoIncorrecto(){
        Long idEmpleado = 1L;
        Long idPropietario = 2L;
        Long idRestaurante = 3L;
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(false);

        boolean respuesta = restauranteUseCase.registrarEmpleado(idEmpleado,idPropietario,idRestaurante);

        assertFalse(respuesta);
    }
    @Test
    void validarExistenciaRestauranteNitRegistrado(){
        when(restaurantePersistencePort.validarExistenciaRestaurante(any())).thenReturn(true);

        assertThrows(NitYaRegistradoException.class,()-> restauranteUseCase.validarExistenciaRestaurante("12345"));
    }
    @Test
    void validarPropietarioRestauranteCasoFalso(){
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(false);

        boolean respuesta = restauranteUseCase.validarPropietarioRestaurante(1L,2L);

        assertFalse(respuesta);
    }
    @Test
    void validarPropietarioRestauranteCasoVerdadero(){
        when(restaurantePersistencePort.validarExistenciaRestaurante(any(),any())).thenReturn(true);

        boolean respuesta = restauranteUseCase.validarPropietarioRestaurante(1L,2L);

        assertTrue(respuesta);
    }
}
