package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.api.IPedidoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

public class PedidoUseCase implements IPedidoServicePort {
    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final IFeignServicePort feignServicePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IPlatoPersistencePort platoPersistencePort, IFeignServicePort feignServicePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.feignServicePort = feignServicePort;
    }

    @Override
    public void generarPedido(Long idRestaurante, List<PedidoPlato> platos) {
        Long idCliente = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        if(Boolean.TRUE.equals(pedidoPersistencePort.verificarPedidoCliente(idCliente))){
           throw new ClientePedidoActivoException("tiene un pedido activo");
        }
        Pedido pedido = new Pedido();
        pedido.setIdRestaurante(restaurantePersistencePort.obtenerRestaurante(idRestaurante));
        pedido.setIdCliente(idCliente);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("Pendiente");
        platos.forEach(plato -> plato.setIdPlato(platoPersistencePort.obtenerPlato(plato.getIdPlato().getId())));
        platos.forEach(plato -> plato.setIdPedido(pedido));

        pedidoPersistencePort.guardarPedido(pedido,platos);
    }

    @Override
    public List<List<Pedido>> obtenerPedidosPorEstado(Long idRestaurante, String estado, int elementos) {
        List<Page<Pedido>> pedidosPlatos = pedidoPersistencePort.obtenerPedidos(idRestaurante, estado, elementos);
        List<List<Pedido>> respuesta = new ArrayList<>();
        pedidosPlatos.forEach(page -> respuesta.add(page.getContent()));

        return respuesta;
    }
}
