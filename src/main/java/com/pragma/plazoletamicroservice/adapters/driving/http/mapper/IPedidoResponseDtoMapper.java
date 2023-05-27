package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface IPedidoResponseDtoMapper {

    @Mapping(target = "restaurante", source = "idRestaurante.nombre")
    PedidoResponseDto toResponse(Pedido pedido);
}
