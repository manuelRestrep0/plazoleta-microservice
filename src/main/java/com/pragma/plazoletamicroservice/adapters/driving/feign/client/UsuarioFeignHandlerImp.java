package com.pragma.plazoletamicroservice.adapters.driving.feign.client;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;

public class UsuarioFeignHandlerImp implements IFeignServicePort {

    private final UsuarioFeignClient usuarioFeignClient;

    public UsuarioFeignHandlerImp(UsuarioFeignClient usuarioFeignClient) {
        this.usuarioFeignClient = usuarioFeignClient;
    }
    @Override
    public boolean validarPropietario(Long id) {
        return usuarioFeignClient.validarPropietario(id);
    }

    @Override
    public String obtenerIdPropietarioFromToken(String token) {
        return usuarioFeignClient.idUsuario(token);
    }
}
