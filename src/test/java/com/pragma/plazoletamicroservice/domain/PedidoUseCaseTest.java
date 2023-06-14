package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.PedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = {PedidoUseCase.class})
@SpringBootTest
class PedidoUseCaseTest {
    @MockBean
    IPedidoPersistencePort pedidoPersistencePort;
    @MockBean
    IRestaurantePersistencePort restaurantePersistencePort;
    @MockBean
    IPlatoPersistencePort platoPersistencePort;
    @MockBean
    IFeignServicePort feignServicePort;
    @InjectMocks
    @Autowired
    PedidoUseCase pedidoUseCase;
    Pedido pedido;
    List<PedidoPlato> platos;

    @BeforeEach
    void setPedido(){

    }
}
