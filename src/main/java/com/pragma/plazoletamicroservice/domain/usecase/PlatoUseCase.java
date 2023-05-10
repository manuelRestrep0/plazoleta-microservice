package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontrado;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;

import java.util.Optional;

public class PlatoUseCase implements IPlatoServicePort {
    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort, IRestauranteRepository restauranteRepository, IRestauranteEntityMapper restauranteEntityMapper) {
        this.platoPersistencePort = platoPersistencePort;
        this.restauranteRepository = restauranteRepository;
        this.restauranteEntityMapper = restauranteEntityMapper;
    }

    @Override
    public void crearPlato(Plato plato) {
        //TODO: FALTA VALIDAR CATEGORIA
        plato.setActivo(false);
        Optional<RestauranteEntity> restaurante = restauranteRepository.findById(plato.getIdRestauranteAux());
        if (restaurante.isPresent()){
            plato.setIdRestaurante(restauranteEntityMapper.toRestaurante(restaurante.get()));
            this.platoPersistencePort.crearPlato(plato);
        } else {
            throw new RestauranteNoEncontrado(Constants.RESTAURANTE_NO_ENCONTRADO);
        }

    }
}
