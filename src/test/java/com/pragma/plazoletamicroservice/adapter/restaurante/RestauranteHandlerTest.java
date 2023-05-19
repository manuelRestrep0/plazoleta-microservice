package com.pragma.plazoletamicroservice.adapter.restaurante;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl.RestauranteHandlerImpl;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IRestauranteRequestMapper;
import com.pragma.plazoletamicroservice.domain.api.IRestauranteServicePort;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
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

@ContextConfiguration(classes = RestauranteHandlerImpl.class)
@SpringBootTest
class RestauranteHandlerTest {
    @MockBean
    IRestauranteServicePort restauranteServicePort;
    @MockBean
    IRestauranteRequestMapper restauranteRequestMapper;
    @InjectMocks
    @Autowired
    RestauranteHandlerImpl restauranteHandler;
    RestauranteRequestDto restauranteRequestDto;

    @BeforeEach
    void setRestauranteRequestDto(){
        restauranteRequestDto = new RestauranteRequestDto(
                "KFC",
                "local80",
                "3024573812",
                "https://twitter.com/home",
                "100111",
                1L
        );
    }
    @Test
    void crearRestaurante(){
        when(restauranteRequestMapper.toRestaurante(any())).thenReturn(new Restaurante());

        restauranteHandler.crearRestaurante(restauranteRequestDto);

        verify(restauranteServicePort).crearRestaurante(restauranteRequestMapper.toRestaurante(restauranteRequestDto));
    }

}
