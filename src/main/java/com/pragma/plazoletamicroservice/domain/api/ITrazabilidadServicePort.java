package com.pragma.plazoletamicroservice.domain.api;

import com.pragma.plazoletamicroservice.domain.model.LogPedido;

public interface ITrazabilidadServicePort {

    void generarLog(LogPedido logPedido);
}
