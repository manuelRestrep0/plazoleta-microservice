package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.CategoriaEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.ICategoriaEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.ICategoriaRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.domain.exceptions.CategoriaNoEncontradaException;
import com.pragma.plazoletamicroservice.domain.exceptions.PropietarioOtroRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.PlatoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

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
    IPlatoRepository platoRepository;
    @MockBean
    IPlatoEntityMapper platoEntityMapper;
    @MockBean
    IRestauranteRepository restauranteRepository;
    @MockBean
    IRestauranteEntityMapper restauranteEntityMapper;
    @MockBean
    ICategoriaRepository categoriaRepository;
    @MockBean
    ICategoriaEntityMapper categoriaEntityMapper;
    @InjectMocks
    @Autowired
    PlatoUseCase platoUseCase;
    Plato plato;

    @BeforeEach
    void setPlato(){
        plato = new Plato(
                1L,
                "Pollo frito",
                new Categoria(),
                2L,
                "pollo frito con especias",
                "20000",
                new Restaurante(),
                3L,
                "urlImagen",
                true
        );
    }
    @Test
    void crearPlatoRestauranteNoExiste(){
        when(restauranteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RestauranteNoEncontradoException.class, () -> platoUseCase.crearPlato(plato));
    }
    @Test
    void crearPlatoRestauranteExistePropietarioNoCoincide(){
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setIdPropietario(20L);
        when(restauranteRepository.findById(any())).thenReturn(Optional.of(restauranteEntity));

        assertThrows(PropietarioOtroRestauranteException.class, () -> platoUseCase.crearPlato(plato));
    }
    @Test
    void crearPlatoCategoriaNoExiste(){
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setIdPropietario(4L);
        when(restauranteRepository.findById(any())).thenReturn(Optional.of(restauranteEntity));
        when(restauranteEntityMapper.toRestaurante(any())).thenReturn(new Restaurante());
        when(categoriaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CategoriaNoEncontradaException.class, () -> platoUseCase.crearPlato(plato));
    }
    @Test
    void crearPlatoCorrectamente(){
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setIdPropietario(4L);
        when(restauranteRepository.findById(any())).thenReturn(Optional.of(restauranteEntity));
        when(restauranteEntityMapper.toRestaurante(any())).thenReturn(new Restaurante());
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(2L);
        when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoriaEntity));
        when(categoriaEntityMapper.toCategoria(any())).thenReturn(new Categoria());

        platoUseCase.crearPlato(plato);

        verify(platoPersistencePort).crearPlato(plato);
    }
}
