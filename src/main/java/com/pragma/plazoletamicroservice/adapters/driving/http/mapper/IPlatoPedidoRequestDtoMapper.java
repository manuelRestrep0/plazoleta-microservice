package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoPedidoRequestDto;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface IPlatoPedidoRequestDtoMapper {

    @Mapping(target = "idPlato.id", source = "idPlato")
    PedidoPlato toPlatoPedido(PlatoPedidoRequestDto platoPedidoRequestDto);
}
