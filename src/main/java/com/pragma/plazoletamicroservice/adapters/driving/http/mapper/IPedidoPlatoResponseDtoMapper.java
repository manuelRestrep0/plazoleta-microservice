package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoPlatoResponseDto;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface IPedidoPlatoResponseDtoMapper {

    PedidoPlatoResponseDto toResponse(PedidoPlato pedidoPlato);

    List<PedidoPlatoResponseDto> toListResponse(List<PedidoPlato> pedidosPlatos);

}
