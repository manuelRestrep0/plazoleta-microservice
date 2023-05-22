package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.RestauranteResponseDto;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface IRestauranteResponseMapper {

    RestauranteResponseDto toResponse(Restaurante restaurante);
}
