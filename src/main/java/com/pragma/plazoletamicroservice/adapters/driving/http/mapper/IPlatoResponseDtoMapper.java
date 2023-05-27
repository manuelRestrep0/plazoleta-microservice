package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PlatoResponseDto;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface IPlatoResponseDtoMapper {


    @Mapping(target = "categoria", source = "idCategoria.nombre")
    PlatoResponseDto toResponse(Plato plato);
    List<PlatoResponseDto> toListResponse(List<Plato> platos);
}
