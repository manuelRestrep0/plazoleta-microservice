package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.PropietarioOtroRestauranteException;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.ICategoriaPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import com.pragma.plazoletamicroservice.domain.utilidades.ObtenerObjetoFromOptional;
import com.pragma.plazoletamicroservice.domain.utilidades.Token;
import com.pragma.plazoletamicroservice.domain.utilidades.ValidacionPermisos;
import org.springframework.data.domain.Page;


public class PlatoUseCase implements IPlatoServicePort {
    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final ICategoriaPersistencePort categoriaPersistencePort;
    private final IFeignServicePort feignServicePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, ICategoriaPersistencePort categoriaPersistencePort, IFeignServicePort feignServicePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.categoriaPersistencePort = categoriaPersistencePort;
        this.feignServicePort = feignServicePort;
    }
    @Override
    public void crearPlato(Plato plato) {
        ValidacionPermisos.validarRol(feignServicePort.obtenerRolFromToken(Token.getToken()), Constantes.ROLE_PROPIETARIO);

        plato.setActivo(true);

        Long idPropietario = Long.parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        Restaurante restaurante = ObtenerObjetoFromOptional.obtenerRestaurante(restaurantePersistencePort.obtenerRestaurante(plato.getIdRestaurante().getId()));
        if(idPropietario.equals(restaurante.getIdPropietario())){
            plato.setIdRestaurante(restaurante);
        } else{
            throw new PropietarioOtroRestauranteException(Constantes.PROPIETARIO_DIFERENTE);
        }

        Categoria categoria = categoriaPersistencePort.obtenerCategoria(plato.getIdCategoria().getId());
        plato.setIdCategoria(categoria);

        this.platoPersistencePort.guardarPlato(plato);
    }
    @Override
    public void modificarPlato(Long id,String precio, String descripcion) {
        Plato plato = validarPropietarioPlatoRestaurante(id);

        if(precio != null){
            plato.setPrecio(precio);
        }
        if(descripcion != null){
            plato.setDescripcion(descripcion);
        }

        platoPersistencePort.guardarPlato(plato);
    }

    @Override
    public void habilitacionPlato(Long id, Boolean estado) {
        Plato plato = validarPropietarioPlatoRestaurante(id);

        plato.setActivo(estado);

        platoPersistencePort.guardarPlato(plato);
    }

    @Override
    public Page<Plato> obtenerPlatos(String nombre, Long id, int elementos, int numeroPagina) {
        return  platoPersistencePort.obtenerPlatos(nombre, id, elementos, numeroPagina);
    }

    Plato validarPropietarioPlatoRestaurante(Long id){
        ValidacionPermisos.validarRol(feignServicePort.obtenerRolFromToken(Token.getToken()),Constantes.ROLE_PROPIETARIO);

        Plato plato = ObtenerObjetoFromOptional.obtenerPlato(platoPersistencePort.obtenerPlato(id));

        Long idPropietario = Long.parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        if(!idPropietario.equals(plato.getIdRestaurante().getIdPropietario())) {
            throw new PropietarioOtroRestauranteException(Constantes.PROPIETARIO_DIFERENTE);
        }
        return plato;
    }
}
