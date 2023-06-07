package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.CodigoIncorrectoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoEstadoNoValidoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoNoExisteException;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.api.IMensajeriaServicePort;
import com.pragma.plazoletamicroservice.domain.api.IPedidoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoRestauranteDiferenteException;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.utilidades.BuilderLogPedido;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import com.pragma.plazoletamicroservice.domain.utilidades.ObtenerObjetoFromOptional;
import com.pragma.plazoletamicroservice.domain.utilidades.Token;
import com.pragma.plazoletamicroservice.domain.utilidades.ValidacionPermisos;
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
    private final IMensajeriaServicePort mensajeriaServicePort;
    private final ITrazabilidadServicePort trazabilidadServicePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IPlatoPersistencePort platoPersistencePort, IFeignServicePort feignServicePort, IMensajeriaServicePort mensajeriaServicePort, ITrazabilidadServicePort trazabilidadServicePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.feignServicePort = feignServicePort;
        this.mensajeriaServicePort = mensajeriaServicePort;
        this.trazabilidadServicePort = trazabilidadServicePort;
    }
    @Override
    public void generarPedido(Long idRestaurante, List<PedidoPlato> platos) {
        Long idCliente = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        if(Boolean.TRUE.equals(pedidoPersistencePort.verificarPedidoCliente(idCliente))){
           throw new ClientePedidoActivoException(Constantes.CLIENTE_PEDIDO_ACTIVO);
        }
        Pedido pedido = new Pedido();
        pedido.setIdRestaurante(ObtenerObjetoFromOptional.obtenerRestaurante(restaurantePersistencePort.obtenerRestaurante(idRestaurante)));
        pedido.setIdCliente(idCliente);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(Constantes.PEDIDO_PENDIENTE);
        platos.forEach(plato ->
                plato.setIdPlato(
                        ObtenerObjetoFromOptional.obtenerPlato(
                                platoPersistencePort.obtenerPlato(
                                        plato.getIdPlato().getId()))));
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
                   throw new PedidoRestauranteDiferenteException("El pedido "+idPedido+" "+Constantes.PEDIDOS_DIFERENTES_RESTAURANTES);
            }
            validarEstadoPedido(idPedido,Constantes.PEDIDO_PENDIENTE);
            validarPedido(idPedido);
            pedidoPersistencePort.actualizarPedido(idPedido,Constantes.PEDIDO_EN_PREPARACION,idEmpleado);

            BuilderLogPedido logPedido = new BuilderLogPedido();
            Long idCliente = pedidoPersistencePort.obtenerIdClienteFromPedido(idPedido);

            logPedido.infoPedido(idPedido,Constantes.PEDIDO_PENDIENTE,Constantes.PEDIDO_EN_PREPARACION);
            logPedido.infoCliente(idCliente, feignServicePort.obtenerCorreoFromUsuario(idCliente));
            logPedido.infoEmpleado(idEmpleado, feignServicePort.obtenerCorreoFromUsuario(idEmpleado));

            trazabilidadServicePort.generarLog(logPedido);
        }
    }
    @Override
    public void marcarPedidoEntregado(Long id, Integer codigo) {
        validarRolEmpleado();
        validarEstadoPedido(id,Constantes.PEDIDO_LISTO);
        validarCodigoVerificacion(id,codigo);
        pedidoPersistencePort.actualizarPedido(id,Constantes.PEDIDO_ENTREGADO);

        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        Long idCliente = pedidoPersistencePort.obtenerIdClienteFromPedido(id);

        BuilderLogPedido logPedido = new BuilderLogPedido();
        logPedido.infoPedido(id,Constantes.PEDIDO_LISTO,Constantes.PEDIDO_ENTREGADO);
        logPedido.infoCliente(idCliente, feignServicePort.obtenerCorreoFromUsuario(idCliente));
        logPedido.infoEmpleado(idEmpleado, feignServicePort.obtenerCorreoFromUsuario(idEmpleado));
        trazabilidadServicePort.generarLog(logPedido);
    }
    @Override
    public void marcarPedidoListo(Long id) {
        validarRolEmpleado();
        validarPedido(id);
        validarEstadoPedido(id, Constantes.PEDIDO_EN_PREPARACION);
        Integer codigo = mensajeriaServicePort.enviarMensaje();
        pedidoPersistencePort.actualizarPedido(id,"Listo");
        pedidoPersistencePort.actualizarPedido(id,codigo);

        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        Long idCliente = pedidoPersistencePort.obtenerIdClienteFromPedido(id);

        BuilderLogPedido logPedido = new BuilderLogPedido();
        logPedido.infoPedido(id,Constantes.PEDIDO_EN_PREPARACION,Constantes.PEDIDO_LISTO);
        logPedido.infoCliente(idCliente, feignServicePort.obtenerCorreoFromUsuario(idCliente));
        logPedido.infoEmpleado(idEmpleado, feignServicePort.obtenerCorreoFromUsuario(idEmpleado));
        trazabilidadServicePort.generarLog(logPedido);
    }
    @Override
    public String cancelarPedido(Long id) {
        Long idCliente = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        if(pedidoPersistencePort.validarPedidoUsuario(id,idCliente)){
            String estado = pedidoPersistencePort.obtenerEstadoPedido(id);
            if(estado.equals(Constantes.PEDIDO_PENDIENTE)){
                pedidoPersistencePort.actualizarPedido(id,Constantes.PEDIDO_CANCELADO);

                Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
                BuilderLogPedido logPedido = new BuilderLogPedido();
                logPedido.infoPedido(id,Constantes.PEDIDO_EN_PREPARACION,Constantes.PEDIDO_LISTO);
                logPedido.infoCliente(idCliente, feignServicePort.obtenerCorreoFromUsuario(idCliente));
                logPedido.infoEmpleado(idEmpleado, feignServicePort.obtenerCorreoFromUsuario(idEmpleado));
                trazabilidadServicePort.generarLog(logPedido);

                return "Pedido cancelado";
            }else{
                return "El pedido no se puede cancelar porque se encuentra en estado: "+estado;
            }
        }
        return "El pedido que intenta cancelar no es suyo";
    }
    private void validarCodigoVerificacion(Long idPedido, Integer codigo){
        if(!pedidoPersistencePort.pedidoVerificarCodigo(idPedido, codigo)){
            throw new CodigoIncorrectoException("Codigo de verificacion incorrecto");
        }
    }
    private void validarPedido(Long id){
        if(!pedidoPersistencePort.pedidoExiste(id)){
           throw new PedidoNoExisteException(Constantes.PEDIDO_NO_REGISTRADO);
        }
    }
    private void validarEstadoPedido(Long idPedido, String estado){
        if(!pedidoPersistencePort.validadEstadoPedido(idPedido, estado)){
            throw new PedidoEstadoNoValidoException("Estado no valido");
        }
    }
    private void validarRolEmpleado(){
        String rolUsuarioActual = feignServicePort.obtenerRolFromToken(Token.getToken());
        ValidacionPermisos.validarRol(rolUsuarioActual,Constantes.ROLE_EMPLEADO);
    }
}
