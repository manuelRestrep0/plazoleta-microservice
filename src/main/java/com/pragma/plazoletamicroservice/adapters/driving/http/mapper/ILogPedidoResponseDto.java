package com.pragma.plazoletamicroservice.adapters.driving.http.mapper;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.LogPedidoResponseDto;
import com.pragma.plazoletamicroservice.domain.model.LogPedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface ILogPedidoResponseDto {
    LogPedidoResponseDto toResponse(LogPedido logPedido);
}
