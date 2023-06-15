package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoDetallesEntity;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoDetallesEntityMapper {
    PedidoDetallesEntity toEntity(PedidoPlato pedidoPlato);
    PedidoPlato toPedidoPlato(PedidoDetallesEntity pedidoDetallesEntity);
}
