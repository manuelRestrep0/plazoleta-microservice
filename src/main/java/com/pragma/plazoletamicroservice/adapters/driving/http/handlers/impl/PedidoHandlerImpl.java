package com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AsignarPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPedidoHandler;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPedidoResponseDtoMapper;
import com.pragma.plazoletamicroservice.adapters.driving.http.mapper.IPlatoPedidoRequestDtoMapper;
import com.pragma.plazoletamicroservice.domain.api.IPedidoServicePort;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoHandlerImpl implements IPedidoHandler {

    private final IPedidoServicePort pedidoServicePort;
    private final IPlatoPedidoRequestDtoMapper platoPedidoRequestDtoMapper;
    private final IPedidoResponseDtoMapper pedidoResponseDtoMapper;

    @Override
    public void generarPedido(Long idRestaurante, List<PlatoPedidoRequestDto> platos) {

        List<PedidoPlato> platosMapeados = new ArrayList<>();
        platos.forEach(plato -> platosMapeados.add(platoPedidoRequestDtoMapper.toPlatoPedido(plato)));
        pedidoServicePort.generarPedido(idRestaurante,platosMapeados);
    }

    @Override
    public List<List<PedidoResponseDto>> obtenerPedidosPorEstado(Long id, String estado, int elementos) {
        List<List<PedidoResponseDto>> pedidos = new ArrayList<>();
        List<List<Pedido>> pedidosPaginados = pedidoServicePort.obtenerPedidosPorEstado(id, estado, elementos);
        pedidosPaginados.forEach(page -> pedidos.add(page.stream().map(pedidoResponseDtoMapper::toResponse).toList()));
        return pedidos;
    }

    @Override
    public void asignarPedidoEmpleado(AsignarPedidoRequestDto asignarPedidoRequestDto) {
        pedidoServicePort.asignarPedido(asignarPedidoRequestDto.getIdRestaurante(), asignarPedidoRequestDto.getPedidos());
    }

    @Override
    public Integer marcarPedido(Long id) {
        return pedidoServicePort.marcarPedido(id);
    }
}
