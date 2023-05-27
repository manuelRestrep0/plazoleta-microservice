package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoEntity;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoEntityMapper {

    PedidoEntity toEntity(Pedido pedido);
    Pedido toPedido(PedidoEntity pedidoEntity);
}
