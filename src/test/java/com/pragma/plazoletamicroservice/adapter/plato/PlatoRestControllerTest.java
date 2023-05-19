package com.pragma.plazoletamicroservice.adapter.plato;

import com.pragma.plazoletamicroservice.adapters.driving.http.controller.PlatoRestController;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPlatoHandler;
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

@ContextConfiguration(classes = PlatoRestController.class)
@SpringBootTest
class PlatoRestControllerTest {
    @MockBean
    IPlatoHandler platoHandler;
    @InjectMocks
    @Autowired
    PlatoRestController platoRestController;
    PlatoRequestDto platoRequestDto;
    ModificarPlatoRequestDto modificarPlatoRequestDto;
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
        modificarPlatoRequestDto = new ModificarPlatoRequestDto(
                1L,
                "60000",
                "Pollo frito sin especias"
        );
    }
    @Test
    void crearPlato(){
        ResponseEntity<Map<String,String>> respuestaEsperada = ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PLATO_CREADO)
        );

        ResponseEntity<Map<String,String>> respuesta = platoRestController.crearPlato(platoRequestDto);

        assertEquals(respuestaEsperada,respuesta);

    }
    @Test
    void modificarPlato(){
        ResponseEntity<Map<String,String>> respuestaEsperada = ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PLATO_MODIFICADO)
        );

        ResponseEntity<Map<String,String>> respuesta = platoRestController.modificarPlato(modificarPlatoRequestDto);

        assertEquals(respuestaEsperada,respuesta);
    }
}
