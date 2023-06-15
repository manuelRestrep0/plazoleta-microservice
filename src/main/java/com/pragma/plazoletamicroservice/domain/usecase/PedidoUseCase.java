package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.CodigoIncorrectoException;
import com.pragma.plazoletamicroservice.domain.exceptions.EmpleadoDiferenteRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoEstadoNoValidoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoNoExisteException;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.api.IMensajeriaServicePort;
import com.pragma.plazoletamicroservice.domain.api.IPedidoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoPlatoDiferenteRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoRestauranteDiferenteException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.model.EficienciaPedidos;
import com.pragma.plazoletamicroservice.domain.model.LogPedido;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.spi.IEmplRestPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoDetallesPersistencePort;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

public class PedidoUseCase implements IPedidoServicePort {
    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final IPedidoDetallesPersistencePort pedidoDetallesPersistencePort;
    private final IEmplRestPersistencePort emplRestPersistencePort;
    private final IFeignServicePort feignServicePort;
    private final IMensajeriaServicePort mensajeriaServicePort;
    private final ITrazabilidadServicePort trazabilidadServicePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort, IRestaurantePersistencePort restaurantePersistencePort, IPlatoPersistencePort platoPersistencePort, IPedidoDetallesPersistencePort pedidoDetallesPersistencePort, IEmplRestPersistencePort emplRestPersistencePort, IFeignServicePort feignServicePort, IMensajeriaServicePort mensajeriaServicePort, ITrazabilidadServicePort trazabilidadServicePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.pedidoDetallesPersistencePort = pedidoDetallesPersistencePort;
        this.emplRestPersistencePort = emplRestPersistencePort;
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
        for (PedidoPlato plato:
             platos) {
            if(Boolean.FALSE.equals(platoPersistencePort.verificarRestaurantePlato(idRestaurante,plato.getIdPlato().getId()))){
                throw new PedidoPlatoDiferenteRestauranteException("Este plato no pertenece a este restaurante.");
            }
        }
        Pedido pedido = new Pedido();
        pedido.setIdRestaurante(ObtenerObjetoFromOptional.obtenerRestaurante(restaurantePersistencePort.obtenerRestaurante(idRestaurante)));
        pedido.setIdCliente(idCliente);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado(Constantes.PEDIDO_PENDIENTE);
        Long idPedido = pedidoPersistencePort.guardarPedido(pedido);
        pedido.setId(idPedido);
        for (PedidoPlato plato:
             platos) {
            plato.setIdPedido(pedido);
            plato.setIdPlato(
                    ObtenerObjetoFromOptional.obtenerPlato(
                            platoPersistencePort.obtenerPlato(
                                    plato.getIdPlato().getId())));
            pedidoDetallesPersistencePort.guardarDetallesPedido(plato);
        }
    }
    @Override
    public Page<Pedido> obtenerPedidosPorEstado(Long idRestaurante, String estado, int elementos, int pagina) {
        validarRolEmpleado();
        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        validarEmpleadoRestaurante(idEmpleado,idRestaurante);

        return pedidoPersistencePort.obtenerPedidos(idRestaurante, estado, elementos, pagina);
    }
    @Override
    public void asignarPedido(Long idRestaurante, List<Long> pedidos) {
        validarRolEmpleado();
        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        validarEmpleadoRestaurante(idEmpleado,idRestaurante);
        for (Long idPedido:
             pedidos) {
            validarPedido(idPedido);
            if(!pedidoPersistencePort.validadRestaurantePedido(idRestaurante,idPedido)){
                   throw new PedidoRestauranteDiferenteException("El pedido "+idPedido+" "+Constantes.PEDIDOS_DIFERENTES_RESTAURANTES);
            }
            validarEstadoPedido(idPedido,Constantes.PEDIDO_PENDIENTE);

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
        validarPedido(id);
        validarEstadoPedido(id,Constantes.PEDIDO_LISTO);

        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        Long idCliente = pedidoPersistencePort.obtenerIdClienteFromPedido(id);

        validarEmpleadoRestauranteFromPedido(idEmpleado,id);
        validarCodigoVerificacion(id,codigo);
        pedidoPersistencePort.actualizarPedido(id,Constantes.PEDIDO_ENTREGADO);

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

        Long idEmpleado = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        Long idCliente = pedidoPersistencePort.obtenerIdClienteFromPedido(id);

        validarEmpleadoRestauranteFromPedido(idEmpleado,id);
        validarEstadoPedido(id, Constantes.PEDIDO_EN_PREPARACION);

        Integer codigo = mensajeriaServicePort.enviarMensaje();

        pedidoPersistencePort.actualizarPedido(id,"Listo");
        pedidoPersistencePort.actualizarPedido(id,codigo);

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

                BuilderLogPedido logPedido = new BuilderLogPedido();
                logPedido.infoPedido(id,Constantes.PEDIDO_EN_PREPARACION,Constantes.PEDIDO_CANCELADO);
                logPedido.infoCliente(idCliente, feignServicePort.obtenerCorreoFromUsuario(idCliente));
                trazabilidadServicePort.generarLog(logPedido);

                return "Pedido cancelado";
            }else{
                return "El pedido no se puede cancelar porque se encuentra en estado: "+estado;
            }
        }
        return "El pedido que intenta cancelar no es suyo";
    }
    @Override
    public EficienciaPedidos obtenerEficianciaRestaurante(Long idRestaurante) {
        List<Pedido> pedidos = pedidoPersistencePort.obtenerPedidosFromRestaurante(idRestaurante);
        pedidos = pedidos.stream().filter(pedido -> pedido.getEstado().equals(Constantes.PEDIDO_ENTREGADO)).toList();
        List<Long> empleados = emplRestPersistencePort.listaEmpleadosFromRestaurante(idRestaurante);
        Map<Long,List<Long>> eficianciaPorEmpleado = new HashMap<>();
        Map<Long,Long> tiempoPedidos = new HashMap<>();
        for (Long empleado:
             empleados) {
            eficianciaPorEmpleado.put(empleado,null);
        }
        for (Pedido pedido:
             pedidos) {
            Long tiempo = trazabilidadServicePort.tiempoPedido(pedido.getId());
            tiempoPedidos.put(pedido.getId(), tiempo);
            List<Long> pedidosEmpleado = eficianciaPorEmpleado.get(pedido.getIdChef());
            if(pedidosEmpleado == null){
                pedidosEmpleado = new ArrayList<>();
            }
            pedidosEmpleado.add(tiempo);
            eficianciaPorEmpleado.put(pedido.getIdChef(),pedidosEmpleado);
        }
        for (Map.Entry<Long,List<Long>> entry:
             eficianciaPorEmpleado.entrySet()) {
            List<Long> tiemposPedidos = entry.getValue();
            Collections.sort(tiemposPedidos);
            entry.setValue(tiemposPedidos);
        }
        EficienciaPedidos eficiencia = new EficienciaPedidos();
        eficiencia.setTiempoPedidos(tiempoPedidos);
        eficiencia.setRankingPedidosPorEmpleado(eficianciaPorEmpleado);

        return eficiencia;
    }
    @Override
    public List<LogPedido> obtenerLogsPedido(Long idPedido) {
        validarPedido(idPedido);
        Long idCliente = parseLong(feignServicePort.obtenerIdUsuarioFromToken(Token.getToken()));
        if(!pedidoPersistencePort.validarPedidoUsuario(idPedido,idCliente)){
            throw new UsuarioNoAutorizadoException("Este pedido no es suyo");
        }
        return trazabilidadServicePort.obtenerLogs(idPedido);
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
    private void validarEmpleadoRestaurante(Long idEmpleado, Long idRestaurante){
        if(Boolean.FALSE.equals(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(idEmpleado,idRestaurante))){
            throw new EmpleadoDiferenteRestauranteException("Este empleado no pertenece al restaurante.");
        }
    }
    private void validarEmpleadoRestauranteFromPedido(Long idEmpleado, Long idPedido){
        Long idRestaurante = pedidoPersistencePort.obtenerIdRestauranteFromPedido(idPedido);
        validarEmpleadoRestaurante(idEmpleado,idRestaurante);
    }
}
