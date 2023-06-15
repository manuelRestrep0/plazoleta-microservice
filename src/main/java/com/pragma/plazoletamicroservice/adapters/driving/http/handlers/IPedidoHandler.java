package com.pragma.plazoletamicroservice.adapters.driving.http.handlers;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AsignarPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.LogPedidoResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;
import com.pragma.plazoletamicroservice.domain.model.EficienciaPedidos;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPedidoHandler {

    void generarPedido(Long idRestaurante, List<PlatoPedidoRequestDto> platos);
    Page<PedidoResponseDto> obtenerPedidosPorEstado(Long id, String estado, int elementos, int pagina);
    void asignarPedidoEmpleado(AsignarPedidoRequestDto pedidos);
    void marcarPedidoListo(Long id);
    void marcarPedidoEntregado(Long id, Integer codigo);
    String cancelarPedido(Long id);
    List<LogPedidoResponseDto> obtenerLogs(Long idPedido);
    EficienciaPedidos obtenerEficiencia(Long idRestaurante);

}
