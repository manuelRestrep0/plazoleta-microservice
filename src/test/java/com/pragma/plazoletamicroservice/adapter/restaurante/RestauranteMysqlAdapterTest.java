package com.pragma.plazoletamicroservice.adapter.restaurante;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter.RestauranteMysqlAdapter;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.domain.exceptions.NitYaRegistradoException;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = RestauranteMysqlAdapter.class)
@SpringBootTest
class RestauranteMysqlAdapterTest {
    @MockBean
    IRestauranteRepository restauranteRepository;
    @MockBean
    IRestauranteEntityMapper restauranteEntityMapper;
    @InjectMocks
    @Autowired
    RestauranteMysqlAdapter restauranteMysqlAdapter;
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
    void crearRestauranteNitYaRegistrado(){
        when(restauranteRepository.findRestauranteEntityByNit(any())).thenReturn(Optional.of(new RestauranteEntity()));

        assertThrows(NitYaRegistradoException.class, () -> restauranteMysqlAdapter.crearRestaurante(restaurante));
    }
    @Test
    void crearRestauranteCorrectamente(){
        when(restauranteRepository.findRestauranteEntityByNit(any())).thenReturn(Optional.empty());
        when(restauranteEntityMapper.toEntity(any())).thenReturn(new RestauranteEntity());

        restauranteMysqlAdapter.crearRestaurante(restaurante);

        verify(restauranteRepository).save(restauranteEntityMapper.toEntity(restaurante));
    }
}
