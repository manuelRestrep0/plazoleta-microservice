package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.PlatoSinModificacionesException;
import com.pragma.plazoletamicroservice.domain.exceptions.PropietarioOtroRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.ICategoriaPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.PlatoUseCase;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = PlatoUseCase.class)
@SpringBootTest
class PlatoUseCaseTest {
    @MockBean
    IPlatoPersistencePort platoPersistencePort;
    @MockBean
    IRestaurantePersistencePort restaurantePersistencePort;
    @MockBean
    ICategoriaPersistencePort categoriaPersistencePort;
    @MockBean
    IFeignServicePort feignServicePort;
    @InjectMocks
    @Autowired
    PlatoUseCase platoUseCase;
    Plato plato;
    Restaurante restaurante;
    Categoria categoria;

    @BeforeEach
    void setPlato(){
        plato = new Plato(
                1L,
                "Pollo frito",
                new Categoria(),
                "pollo frito con especias",
                "20000",
                new Restaurante(),
                "urlImagen",
                true
        );
        plato.getIdRestaurante().setId(3L);
        plato.getIdCategoria().setId(2L);
        restaurante = new Restaurante(
                3L,
                "nombre",
                "123",
                "local",
                "3024261812",
                "url",
                2L
        );
        categoria = new Categoria(
                2L,
                "Nombre categoria",
                "description"
        );

    }
    @Test
    void crearPlatoCasoExitoso(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(Optional.of(restaurante));
        when(categoriaPersistencePort.obtenerCategoria(any())).thenReturn(categoria);

        platoUseCase.crearPlato(plato);

        assertEquals(restaurante,plato.getIdRestaurante());
        assertEquals(categoria,plato.getIdCategoria());
        assertEquals(true,plato.getActivo());
    }
    @Test
    void crearPlatoRolNoAutorizado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(Optional.of(restaurante));
        when(categoriaPersistencePort.obtenerCategoria(any())).thenReturn(categoria);

        assertThrows(UsuarioNoAutorizadoException.class, ()-> platoUseCase.crearPlato(plato));
    }
    @Test
    void crearPlatoDiferentePropietario(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("3");
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(Optional.of(restaurante));
        when(categoriaPersistencePort.obtenerCategoria(any())).thenReturn(categoria);

        assertThrows(PropietarioOtroRestauranteException.class,()->platoUseCase.crearPlato(plato));
    }
    @Test
    void modificarPlatoAmbosCampos(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        platoUseCase.modificarPlato(2L,"500","prueba");

        assertEquals("500",plato.getPrecio());
        assertEquals("prueba",plato.getDescripcion());
    }
    @Test
    void modificarPlatoPrecio(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        platoUseCase.modificarPlato(2L,"500",null);

        assertEquals("500",plato.getPrecio());
    }
    @Test
    void modificarPlatoDescription(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        platoUseCase.modificarPlato(2L,null,"prueba");

        assertEquals("prueba",plato.getDescripcion());
    }
    @Test
    void modificarPlatoRolNoAutorizado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        assertThrows(UsuarioNoAutorizadoException.class,()->platoUseCase.modificarPlato(2L,"500","prueba"));
    }
    @Test
    void modificarPlatoCamposVacios(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        assertThrows(PlatoSinModificacionesException.class,()->platoUseCase.modificarPlato(2L,null,null));
    }
    @Test
    void habilitarPlatoEstadoActivo(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        platoUseCase.habilitacionPlato(2L,true);

        assertTrue(plato.getActivo());
    }
    @Test
    void deshabilitarPlatoEstadoActivo(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_PROPIETARIO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        platoUseCase.habilitacionPlato(2L,false);

        assertFalse(plato.getActivo());
    }
    @Test
    void habilitarPlatoRolNoAutorizado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("2");
        plato.setIdRestaurante(restaurante);

        assertThrows(UsuarioNoAutorizadoException.class,()->platoUseCase.habilitacionPlato(2L,true));
    }
}
