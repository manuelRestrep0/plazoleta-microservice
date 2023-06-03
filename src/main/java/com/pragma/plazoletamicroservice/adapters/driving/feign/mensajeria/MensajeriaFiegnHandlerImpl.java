package com.pragma.plazoletamicroservice.adapters.driving.feign.mensajeria;

import com.pragma.plazoletamicroservice.domain.api.IMensajeriaServicePort;

public class MensajeriaFiegnHandlerImpl implements IMensajeriaServicePort {

    private final MensajeriaFeignClient mensajeriaFeignClient;

    public MensajeriaFiegnHandlerImpl(MensajeriaFeignClient mensajeriaFeignClient) {
        this.mensajeriaFeignClient = mensajeriaFeignClient;
    }

    @Override
    public Integer enviarMensaje() {
        return mensajeriaFeignClient.enviarMensajeVerificacion();
    }
}
