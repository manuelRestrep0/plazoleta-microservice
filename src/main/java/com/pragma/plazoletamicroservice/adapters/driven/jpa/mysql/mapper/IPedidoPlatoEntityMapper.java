package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoPlatoEntity;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPedidoPlatoEntityMapper {
    PedidoPlatoEntity toEntity(PedidoPlato pedidoPlato);
    PedidoPlato toPedidoPlato(PedidoPlatoEntity pedidoPlatoEntity);
}
