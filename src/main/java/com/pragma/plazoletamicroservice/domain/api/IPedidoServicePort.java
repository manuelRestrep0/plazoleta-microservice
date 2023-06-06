package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;

import java.util.List;

public interface IPedidoServicePort {

    void generarPedido(Long idRestaurante, List<PedidoPlato> platos);
    List<List<Pedido>> obtenerPedidosPorEstado(Long idRestaurante,String estado, int elementos);
    void asignarPedido(Long idRestaurante, List<Long> pedidos);
    void marcarPedidoEntregado(Long id, Integer codigo);
    void marcarPedidoListo(Long id);
    String cancelarPedido(Long id);
}
