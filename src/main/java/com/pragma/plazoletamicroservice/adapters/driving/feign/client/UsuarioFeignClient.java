package com.pragma.plazoletamicroservice.adapters.driving.feign.client;

import com.pragma.plazoletamicroservice.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USUARIO-MICROSERVICE-API", url = "http://localhost:8080/usuario", configuration = FeignClientConfig.class)
public interface UsuarioFeignClient {

    @GetMapping(value = "/validar-propietario/{id}")
    Boolean validarPropietario(@PathVariable("id") Long id);
}
