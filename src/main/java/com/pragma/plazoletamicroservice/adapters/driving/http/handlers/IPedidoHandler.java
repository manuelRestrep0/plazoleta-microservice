package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AsignarPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;

import java.util.List;

public interface IPedidoHandler {

    void generarPedido(Long idRestaurante, List<PlatoPedidoRequestDto> platos);

    List<List<PedidoResponseDto>> obtenerPedidosPorEstado(Long id, String estado, int elementos);

    void asignarPedidoEmpleado(AsignarPedidoRequestDto pedidos);

    Integer marcarPedidoListo(Long id);
}
