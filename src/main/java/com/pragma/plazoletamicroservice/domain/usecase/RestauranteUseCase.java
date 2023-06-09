package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.exceptions.NitYaRegistradoException;
import com.pragma.plazoletamicroservice.domain.api.IRestauranteServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoPropietarioException;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.spi.IEmplRestPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import com.pragma.plazoletamicroservice.domain.utilidades.Token;
import com.pragma.plazoletamicroservice.domain.utilidades.ValidacionPermisos;
import org.springframework.data.domain.Page;

import java.util.List;

public class RestauranteUseCase implements IRestauranteServicePort {
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IEmplRestPersistencePort emplRestPersistencePort;
    private final IFeignServicePort feignServicePort;

    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort, IEmplRestPersistencePort emplRestPersistencePort, IFeignServicePort feignServicePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.emplRestPersistencePort = emplRestPersistencePort;
        this.feignServicePort = feignServicePort;
    }
    @Override
    public void crearRestaurante(Restaurante restaurante) {
        String rolUsuarioActual = feignServicePort.obtenerRolFromToken(Token.getToken());
        ValidacionPermisos.validarRol(rolUsuarioActual, Constantes.ROLE_ADMINISTRADOR);

        if(!feignServicePort.validarPropietario(restaurante.getIdPropietario())){
            throw new UsuarioNoPropietarioException(Constantes.USUARIO_NO_PROPIETARIO);
        }
        validarExistenciaRestaurante(restaurante.getNit());

        this.restaurantePersistencePort.crearRestaurante(restaurante);
    }
    @Override
    public List<Page<Restaurante>> obtenerRestaurantes(int elementos) {
        return restaurantePersistencePort.obtenerRestaurantes(elementos);
    }
    @Override
    public boolean registrarEmpleado(Long idEmpleado, Long idPropietario, Long idRestaurante) {
        if(!restaurantePersistencePort.validarExistenciaRestaurante(idRestaurante,idPropietario)){
            return false;
        }
        emplRestPersistencePort.guardarEmpleadoRestaurante(idEmpleado,idRestaurante);
        return true;
    }
    private void validarExistenciaRestaurante(String nit){
        if(Boolean.TRUE.equals(restaurantePersistencePort.validarExistenciaRestaurante(nit))){
            throw new NitYaRegistradoException(Constantes.NIT_YA_REGISTRADO);
        }
    }
}
