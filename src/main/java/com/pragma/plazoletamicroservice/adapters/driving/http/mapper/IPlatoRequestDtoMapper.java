package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface IPlatoRequestDtoMapper {

    @Mapping(source = "idCategoria", target = "idCategoriaAux")
    @Mapping(source = "idRestaurante", target = "idRestauranteAux")
    @Mapping(target = "idRestaurante", ignore = true)
    @Mapping(target = "idCategoria", ignore = true)
    Plato toPlato(PlatoRequestDto platoRequestDto);
}
