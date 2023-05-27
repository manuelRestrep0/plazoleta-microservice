package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPedidoPersistencePort {

    void guardarPedido(Pedido pedido, List<PedidoPlato> platosPedidos);

    Boolean verificarPedidoCliente(Long idCliente);

    List<Page<Pedido>> obtenerPedidos(Long id, String estado, int elementos);

}
