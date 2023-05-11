package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.api.IRestauranteServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoPropietarioException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;

public class RestauranteUseCase implements IRestauranteServicePort {
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final UsuarioFeignClient feignClient;

    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, UsuarioFeignClient feignClient) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.feignClient = feignClient;
    }

    @Override
    public void crearRestaurante(Restaurante restaurante) {
        if(Boolean.FALSE.equals(feignClient.validarPropietario(restaurante.getIdPropietario()))){
            throw new UsuarioNoPropietarioException(Constants.USUARIO_NO_PROPIETARIO);
        }
        this.restaurantePersistencePort.crearRestaurante(restaurante);
    }
}
