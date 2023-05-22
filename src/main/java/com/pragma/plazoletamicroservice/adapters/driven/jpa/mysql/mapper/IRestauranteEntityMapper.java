package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface IRestauranteEntityMapper {

    RestauranteEntity toEntity(Restaurante restaurante);
    Restaurante toRestaurante(RestauranteEntity restauranteEntity);
}
