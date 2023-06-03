package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.exceptions.PedidoNoExisteException;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.api.IMensajeriaServicePort;
import com.pragma.plazoletamicroservice.domain.api.IPedidoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoRestauranteDiferenteException;
import com.pragma.plazoletamicroservice.domain.exceptions.PlatoNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import com.pragma.plazoletamicroservice.domain.utilidades.Token;
import com.pragma.plazoletamicroservice.domain.utilidades.ValidacionPermisos;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

public class PedidoUseCase implements IPedidoServicePort {
    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final IFeignServicePort feignServicePort;
    private final IMensajeriaServicePort mensajeriaServicePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IPlatoPersistencePort platoPersistencePort, IFeignServicePort feignServicePort, IMensajeriaServicePort mensajeriaServicePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.feignServicePort = feignServicePort;
        this.mensajeriaServicePort = mensajeriaServicePort;
    }

    @Override
    public void generarPedido(Long idRestaurante, List<PedidoPlato> platos) {
        Long idCliente = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        if(Boolean.TRUE.equals(pedidoPersistencePort.verificarPedidoCliente(idCliente))){
           throw new ClientePedidoActivoException(Constantes.CLIENTE_PEDIDO_ACTIVO);
        }
        Pedido pedido = new Pedido();
        pedido.setIdRestaurante(obtenerRestaurante(restaurantePersistencePort.obtenerRestaurante(idRestaurante)));
        pedido.setIdCliente(idCliente);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(Constantes.PEDIDO_PENDIENTE);
        platos.forEach(plato -> plato.setIdPlato(obtenerPlato(platoPersistencePort.obtenerPlato(plato.getIdPlato().getId()))));
        platos.forEach(plato -> plato.setIdPedido(pedido));

        pedidoPersistencePort.guardarPedido(pedido,platos);
    }

    @Override
    public List<List<Pedido>> obtenerPedidosPorEstado(Long idRestaurante, String estado, int elementos) {
        validarRolEmpleado();

        List<Page<Pedido>> pedidosPlatos = pedidoPersistencePort.obtenerPedidos(idRestaurante, estado, elementos);
        List<List<Pedido>> respuesta = new ArrayList<>();
        pedidosPlatos.forEach(page -> respuesta.add(page.getContent()));

        return respuesta;
    }

    @Override
    public void asignarPedido(Long idRestaurante, List<Long> pedidos) {
        validarRolEmpleado();
        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        for (Long idPedido:
             pedidos) {
            if(!pedidoPersistencePort.validadRestaurantePedido(idRestaurante,idPedido)){
                   throw new PedidoRestauranteDiferenteException("El pedido "+idPedido+Constantes.PEDIDOS_DIFERENTES_RESTAURANTES);
            }
            validarPedido(idPedido);
            pedidoPersistencePort.actualizarPedido(idPedido,Constantes.PEDIDO_EN_PREPARACION,idEmpleado);
        }
    }

    @Override
    public Integer marcarPedido(Long id) {
        validarRolEmpleado();
        validarPedido(id);
        pedidoPersistencePort.actualizarPedido(id,"Listo");
        return mensajeriaServicePort.enviarMensaje();
    }
    private void validarPedido(Long id){
        if(!pedidoPersistencePort.pedidoExiste(id)){
           throw new PedidoNoExisteException(Constantes.PEDIDO_NO_REGISTRADO);
        }
    }
    private void validarRolEmpleado(){
        String rolUsuarioActual = feignServicePort.obtenerRolFromToken(Token.getToken());
        ValidacionPermisos.validarRol(rolUsuarioActual,Constantes.ROLE_EMPLEADO);
    }
    private Restaurante obtenerRestaurante(Optional<Restaurante> restaurante){
        if (restaurante.isEmpty()){
            throw new RestauranteNoEncontradoException(Constantes.RESTAURANTE_NO_ENCONTRADO);
        }
        return restaurante.get();
    }
    private Plato obtenerPlato(Optional<Plato> plato){
        if(plato.isEmpty()){
            throw new PlatoNoEncontradoException(Constantes.PLATO_NO_REGISTRADO);
        }
        return plato.get();
    }

}
