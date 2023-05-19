package com.pragma.plazoletamicroservice.adapter.restaurante;

import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.adapters.driving.http.controller.RestauranteRestController;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IRestauranteHandler;
import com.pragma.plazoletamicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = RestauranteRestController.class)
@SpringBootTest
class RestauranteRestControllerTest {
    @MockBean
    IRestauranteHandler restauranteHandler;
    @MockBean
    UsuarioFeignClient usuarioFeignClient;
    @InjectMocks
    @Autowired
    RestauranteRestController restauranteRestController;
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
        ResponseEntity<Map<String,String>> respuestaEsperada = ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.CREACION_EXITOSA_RESTAURANTE)
        );

        ResponseEntity<Map<String,String>> respuesta = restauranteRestController.crearRestaurante(restauranteRequestDto);

        assertEquals(respuestaEsperada,respuesta);
    }
}
