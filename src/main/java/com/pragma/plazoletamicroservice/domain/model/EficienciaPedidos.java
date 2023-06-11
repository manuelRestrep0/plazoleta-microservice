package com.pragma.plazoletamicroservice.domain.model;

import java.util.List;
import java.util.Map;

public class EficienciaPedidos {

    Map<Long,Long> tiempoPedidos;
    Map<Long,List<Long>> rankingPedidosPorEmpleado;

    public EficienciaPedidos() {
    }

    public EficienciaPedidos(Map<Long, Long> tiempoPedidos, Map<Long, List<Long>> rankingPedidosPorEmpleado) {
        this.tiempoPedidos = tiempoPedidos;
        this.rankingPedidosPorEmpleado = rankingPedidosPorEmpleado;
    }

    public Map<Long, Long> getTiempoPedidos() {
        return tiempoPedidos;
    }

    public void setTiempoPedidos(Map<Long, Long> tiempoPedidos) {
        this.tiempoPedidos = tiempoPedidos;
    }

    public Map<Long, List<Long>> getRankingPedidosPorEmpleado() {
        return rankingPedidosPorEmpleado;
    }

    public void setRankingPedidosPorEmpleado(Map<Long, List<Long>> rankingPedidosPorEmpleado) {
        this.rankingPedidosPorEmpleado = rankingPedidosPorEmpleado;
    }
}
