package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestauranteRepository extends JpaRepository<RestauranteEntity,Long> {

    Optional<RestauranteEntity> findRestauranteEntityByNit(String nit);
}
