package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoDetallesEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoDetallesPK;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPedidoDetallesEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPedidoDetallesRepository;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoDetallesPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PedidoDetallesMysqlAdapter implements IPedidoDetallesPersistencePort {
    private final IPedidoDetallesRepository pedidoPlatoRepository;
    private final IPedidoDetallesEntityMapper pedidoPlatoEntityMapper;
    @Override
    public void guardarDetallesPedido(PedidoPlato pedidoPlato) {
        PedidoDetallesPK pedidoDetallesPK = new PedidoDetallesPK(pedidoPlato.getIdPedido().getId(), pedidoPlato.getIdPlato().getId());
        PedidoDetallesEntity pedidoDetallesEntity = pedidoPlatoEntityMapper.toEntity(pedidoPlato);
        pedidoDetallesEntity.setId(pedidoDetallesPK);
        pedidoPlatoRepository.save(pedidoDetallesEntity);
    }
}
