package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@RequiredArgsConstructor
public class RestauranteMysqlAdapter implements IRestaurantePersistencePort {
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;


    @Override
    public void crearRestaurante(Restaurante restaurante) {
        restauranteRepository.save(restauranteEntityMapper.toEntity(restaurante));
    }

    @Override
    public Optional<Restaurante> obtenerRestaurante(Long id) {
        Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(id);

        return restauranteEntity.map(restauranteEntityMapper::toRestaurante);
    }
    @Override
    public Page<Restaurante> obtenerRestaurantes(int elementos, int numeroPagina) {
        Pageable pageable = PageRequest.of(numeroPagina, elementos, Sort.by("nombre"));
        return restauranteRepository.findAll(pageable).map(restauranteEntityMapper::toRestaurante);
    }
    @Override
    public Boolean validarExistenciaRestaurante(String nit) {
        return restauranteRepository.existsByNit(nit);
    }
    @Override
    public Boolean validarExistenciaRestaurante(Long idPropietario, Long idRestaurante) {
        return restauranteRepository.existsByIdAndIdPropietario(idRestaurante,idPropietario);
    }

}
