package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.LogPedido;

import java.util.List;

public interface ITrazabilidadServicePort {

    void generarLog(LogPedido logPedido);
    Long tiempoPedido(Long idPedido);
    List<LogPedido> obtenerLogs(Long idPedido);

}
