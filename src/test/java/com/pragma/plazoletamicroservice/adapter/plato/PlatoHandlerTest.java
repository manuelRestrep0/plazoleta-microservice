package com.pragma.plazoletamicroservice.adapter.plato;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl.PlatoHandlerImpl;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPlatoRequestDtoMapper;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPlatoResponseDtoMapper;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import com.pragma.plazoletamicroservice.domain.model.Plato;
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

@ContextConfiguration(classes = PlatoHandlerImpl.class)
@SpringBootTest
class PlatoHandlerTest {
    @MockBean
    IPlatoServicePort platoServicePort;
    @MockBean
    IPlatoRequestDtoMapper platoRequestDtoMapper;
    @MockBean
    IPlatoResponseDtoMapper platoResponseDtoMapper;
    @InjectMocks
    @Autowired
    PlatoHandlerImpl platoHandler;
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
    }
    @Test
    void crearPlato(){
        when(platoRequestDtoMapper.toPlato(any())).thenReturn(new Plato());

        platoHandler.crearPlato(platoRequestDto);

        verify(platoServicePort).crearPlato(platoRequestDtoMapper.toPlato(platoRequestDto));
    }
    @Test
    void modificarPlato(){
        modificarPlatoRequestDto = new ModificarPlatoRequestDto(
                1L,
                "60000",
                "Plato Comida"
        );
        Long id = modificarPlatoRequestDto.getId();
        String precio = modificarPlatoRequestDto.getPrecio();
        String descripcion = modificarPlatoRequestDto.getDescripcion();

        platoHandler.modificarPlato(modificarPlatoRequestDto);

        verify(platoServicePort).modificarPlato(id,precio,descripcion);
    }
}
