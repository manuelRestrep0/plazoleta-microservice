package com.pragma.plazoletamicroservice.adapters.driving.feign.trazabilidad;

import com.pragma.plazoletamicroservice.configuration.FeignClientConfig;
import com.pragma.plazoletamicroservice.domain.model.LogPedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "TRAZABILIDAD-MICROSERVICIO-API", url = "http://localhost:8091", configuration = FeignClientConfig.class)
public interface TrazabilidadFeignClient {

    @PostMapping("/logs/pedidos/registro")
    void generarLog(LogPedido logPedido);

    @GetMapping("/logs/pedidos/tiempo-pedido/{idPedido}")
    Long obtenerTiempoPedido(@PathVariable("idPedido") Long idPedido);
    @GetMapping("/logs/pedidos/obtener-logs/{idPedido}")
    List<LogPedido> obtenerLogsPedido(@PathVariable("idPedido") Long idPedido);

}
