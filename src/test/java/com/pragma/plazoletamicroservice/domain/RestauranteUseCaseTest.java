package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoPropietarioException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.RestauranteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = RestauranteUseCase.class)
@SpringBootTest
class RestauranteUseCaseTest {

    @MockBean
    IRestaurantePersistencePort restaurantePersistencePort;
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
    void crearRestaurante(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_ADMINISTRADOR");
        when(feignServicePort.validarPropietario(any())).thenReturn(true);

        restauranteUseCase.crearRestaurante(restaurante);

        verify(restaurantePersistencePort).crearRestaurante(restaurante);
    }
    @Test
    void crearRestauranteUsuarioNoAutorizado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");
        when(feignServicePort.validarPropietario(any())).thenReturn(true);

        assertThrows(UsuarioNoAutorizadoException.class, () -> restauranteUseCase.crearRestaurante(restaurante));
    }
    @Test
    void crearRestauranteNoPropietario(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_ADMINISTRADOR");
        when(feignServicePort.validarPropietario(any())).thenReturn(false);

        assertThrows(UsuarioNoPropietarioException.class, () -> restauranteUseCase.crearRestaurante(restaurante));
    }
    @Test
    void obtenerPaginasRestaurantes(){
        restauranteUseCase.obtenerRestauranres(5);

        verify(restaurantePersistencePort).obtenerRestaurantes(5);
    }
}
