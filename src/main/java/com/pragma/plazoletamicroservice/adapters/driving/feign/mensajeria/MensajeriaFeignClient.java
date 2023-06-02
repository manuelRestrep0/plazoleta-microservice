package com.pragma.plazoletamicroservice.adapters.driving.feign.mensajeria;

import com.pragma.plazoletamicroservice.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MENSAJERIA-MICROSERVICIO-API", url = "http://localhost:8081", configuration = FeignClientConfig.class)
public interface MensajeriaFeignClient {

    @GetMapping("/mensajeria/enviar-mensaje")
    Integer enviarMensajeVerificacion();
}
