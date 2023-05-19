package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.exceptions.NitYaRegistradoException;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestauranteMysqlAdapter implements IRestaurantePersistencePort {
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;


    @Override
    public void crearRestaurante(Restaurante restaurante) {
        if(restauranteRepository.findRestauranteEntityByNit(restaurante.getNit()).isPresent()){
            throw new NitYaRegistradoException(Constants.NIT_YA_REGISTRADO);
        }
        restauranteRepository.save(restauranteEntityMapper.toEntity(restaurante));
    }

    @Override
    public Restaurante obtenerRestaurante(Long id) {
        Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(id);
        if(restauranteEntity.isEmpty()){
            throw new RestauranteNoEncontradoException(Constants.RESTAURANTE_NO_ENCONTRADO);
        }
        return restauranteEntityMapper.toRestaurante(restauranteEntity.get());
    }
}
