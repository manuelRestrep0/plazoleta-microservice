package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AsignarPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;

import java.util.List;

public interface IPedidoHandler {

    void generarPedido(Long idRestaurante, List<PlatoPedidoRequestDto> platos);

    List<List<PedidoResponseDto>> obtenerPedidosPorEstado(Long id, String estado, int elementos);

    void asignarPedidoEmpleado(AsignarPedidoRequestDto pedidos);

    void marcarPedidoListo(Long id);
    void marcarPedidoEntregado(Long id, Integer codigo);
    String cancelarPedido(Long id);
}
