package com.pragma.plazoletamicroservice.domain.spi;

import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPedidoPersistencePort {

    void guardarPedido(Pedido pedido, List<PedidoPlato> platosPedidos);
    Boolean verificarPedidoCliente(Long idCliente);
    Page<Pedido> obtenerPedidos(Long id, String estado, int elementos, int numeroPagina);
    boolean pedidoExiste(Long id);
    boolean pedidoVerificarCodigo(Long id, Integer codigo);
    void actualizarPedido(Long idPedido, String estado, Long idChef);
    void actualizarPedido(Long idPedido, String estado);
    void actualizarPedido(Long idPedido, Integer codigoVerificacion);
    boolean validadRestaurantePedido(Long idRestaurante, Long idPedido);
    boolean validadEstadoPedido(Long id,String estado);
    boolean validarPedidoUsuario(Long id, Long idCliente);
    String obtenerEstadoPedido(Long id);
    Long obtenerIdClienteFromPedido(Long idPedido);
    Long obtenerIdRestauranteFromPedido(Long idPedido);
    List<Pedido> obtenerPedidosFromRestaurante(Long idRestaurante);

}
