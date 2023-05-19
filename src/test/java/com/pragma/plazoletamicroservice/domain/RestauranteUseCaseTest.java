package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoPropietarioException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.RestauranteUseCase;
import feign.FeignException;
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
    UsuarioFeignClient feignClient;
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
    void crearRestauranteUsuarioNoPropietario(){
        when(feignClient.validarPropietario(any())).thenReturn(false);

        assertThrows(UsuarioNoPropietarioException.class, () -> restauranteUseCase.crearRestaurante(restaurante));
    }
    @Test
    void crearRestauranteUsuarioNoExiste(){
        when(feignClient.validarPropietario(any())).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> restauranteUseCase.crearRestaurante(restaurante));
    }
    @Test
    void crearPropietarioCorrectamente(){
        when(feignClient.validarPropietario(any())).thenReturn(true);

        restauranteUseCase.crearRestaurante(restaurante);

        verify(restaurantePersistencePort).crearRestaurante(restaurante);
    }
}
