package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.CategoriaEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.ICategoriaEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.ICategoriaRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.CategoriaNoEncontradaException;
import com.pragma.plazoletamicroservice.domain.exceptions.PropietarioOtroRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.ICategoriaPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.PlatoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


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

    @BeforeEach
    void setPlato(){
        plato = new Plato(
                1L,
                "Pollo frito",
                new Categoria(
                        2L,
                        "Nombre categoria",
                        "description"
                ),
                2L,
                "pollo frito con especias",
                "20000",
                new Restaurante(
                        3L,
                        "nombre",
                        "123",
                        "local",
                        "3024261812",
                        "url",
                        2L
                ),
                3L,
                "urlImagen",
                true
        );
    }
    @Test
    void crearPlato(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_PROPIETARIO");
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(new Restaurante(
                3L,
                "nombre",
                "123",
                "local",
                "3024261812",
                "url",
                2L
        ));
        when(categoriaPersistencePort.obtenerCategoria(any())).thenReturn(new Categoria(
                2L,
                "Nombre categoria",
                "description"
        ));
        when(feignServicePort.obtenerIdPropietarioFromToken(any())).thenReturn("2");

        platoUseCase.crearPlato(plato);

        verify(platoPersistencePort).crearPlato(plato);
    }
    @Test
    void crearPlatoUsuarioNoAutorizado(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(new Restaurante(
                3L,
                "nombre",
                "123",
                "local",
                "3024261812",
                "url",
                2L
        ));
        when(categoriaPersistencePort.obtenerCategoria(any())).thenReturn(new Categoria(
                2L,
                "Nombre categoria",
                "description"
        ));
        when(feignServicePort.obtenerIdPropietarioFromToken(any())).thenReturn("2");

        assertThrows(UsuarioNoAutorizadoException.class, () -> platoUseCase.crearPlato(plato));
    }
    @Test
    void crearPlatoPropietarioNoCoincide(){
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_PROPIETARIO");
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(new Restaurante(
                3L,
                "nombre",
                "123",
                "local",
                "3024261812",
                "url",
                6L
        ));
        when(categoriaPersistencePort.obtenerCategoria(any())).thenReturn(new Categoria(
                2L,
                "Nombre categoria",
                "description"
        ));
        when(feignServicePort.obtenerIdPropietarioFromToken(any())).thenReturn("2");

        assertThrows(PropietarioOtroRestauranteException.class, () -> platoUseCase.crearPlato(plato));
    }
    @Test
    void modificarPlato(){
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(plato);
        when(feignServicePort.obtenerIdPropietarioFromToken(any())).thenReturn("2");
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_PROPIETARIO");

        platoUseCase.modificarPlato(1L,"1000","plato modificado");

        verify(platoPersistencePort).modificarPlato(plato);
    }
    @Test
    void modificarPlatoUsuarioNoAutorizado(){
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(plato);
        when(feignServicePort.obtenerIdPropietarioFromToken(any())).thenReturn("2");
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");

        assertThrows(UsuarioNoAutorizadoException.class, () -> platoUseCase.modificarPlato(1L,"1000","plato modificado"));
    }
    @Test
    void modificarPlatoPropietarioNoCoincide(){
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(plato);
        when(feignServicePort.obtenerIdPropietarioFromToken(any())).thenReturn("3");
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_PROPIETARIO");

        assertThrows(PropietarioOtroRestauranteException.class, () -> platoUseCase.modificarPlato(1L,"1000","plato modificado"));
    }

}
