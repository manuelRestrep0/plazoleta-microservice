package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;

public interface IPedidoDetallesPersistencePort {

    void guardarDetallesPedido(PedidoPlato pedidoPlato);
}
