package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.api.IRestauranteServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoPropietarioException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;

public class RestauranteUseCase implements IRestauranteServicePort {
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IFeignServicePort feignServicePort;

    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IFeignServicePort feignServicePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.feignServicePort = feignServicePort;
    }
    @Override
    public void crearRestaurante(Restaurante restaurante) {
        Long idPropietario = Long.parseLong(feignServicePort.obtenerIdPropietarioFromToken(Token.getToken()));
        if(!feignServicePort.validarPropietario(idPropietario)){
            throw new UsuarioNoPropietarioException(Constants.USUARIO_NO_PROPIETARIO);
        }
        restaurante.setIdPropietario(idPropietario);
        this.restaurantePersistencePort.crearRestaurante(restaurante);
    }
}
