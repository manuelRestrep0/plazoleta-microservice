package com.pragma.plazoletamicroservice.adapters.driving.feign.trazabilidad;

import com.pragma.plazoletamicroservice.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoletamicroservice.domain.model.LogPedido;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrazabilidadFeignHandlerImpl implements ITrazabilidadServicePort {

    private final TrazabilidadFeignClient trazabilidadFeignClient;
    @Override
    public void generarLog(LogPedido logPedido) {
        trazabilidadFeignClient.generarLog(logPedido);
    }
    @Override
    public Long tiempoPedido(Long idPedido) {
        return trazabilidadFeignClient.obtenerTiempoPedido(idPedido);
    }
}
