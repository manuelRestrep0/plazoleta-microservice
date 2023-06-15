package com.pragma.plazoletamicroservice.domain;

import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.api.IMensajeriaServicePort;
import com.pragma.plazoletamicroservice.domain.api.ITrazabilidadServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.exceptions.CodigoIncorrectoException;
import com.pragma.plazoletamicroservice.domain.exceptions.EmpleadoDiferenteRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoEstadoNoValidoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoNoExisteException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoPlatoDiferenteRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import com.pragma.plazoletamicroservice.domain.model.LogPedido;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.model.Restaurante;
import com.pragma.plazoletamicroservice.domain.spi.IEmplRestPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoDetallesPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.PedidoUseCase;
import com.pragma.plazoletamicroservice.domain.utilidades.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = {PedidoUseCase.class})
@SpringBootTest
class PedidoUseCaseTest {
    @MockBean
    IPedidoPersistencePort pedidoPersistencePort;
    @MockBean
    IRestaurantePersistencePort restaurantePersistencePort;
    @MockBean
    IPlatoPersistencePort platoPersistencePort;
    @MockBean
    IPedidoDetallesPersistencePort pedidoDetallesPersistencePort;
    @MockBean
    IEmplRestPersistencePort emplRestPersistencePort;
    @MockBean
    IFeignServicePort feignServicePort;
    @MockBean
    IMensajeriaServicePort mensajeriaServicePort;
    @MockBean
    ITrazabilidadServicePort trazabilidadServicePort;
    @InjectMocks
    @Autowired
    PedidoUseCase pedidoUseCase;
    Pedido pedido;
    Restaurante restaurante;
    Plato plato;
    Categoria categoria;
    List<PedidoPlato> platos;
    @BeforeEach
    void setPedido(){
        pedido = new Pedido(
                1L,
                LocalDate.now(),
                null,
                null,
                null,
                2L
        );
        restaurante = new Restaurante(
                1L,
                "kfc",
                "123",
                "local80",
                "3024261712",
                "https://twitter.com/home",
                2L
        );
        plato = new Plato(
                1L,
                "Pollo frito",
                categoria,
                "pollo frito con especias",
                "20000",
                restaurante,
                "urlImagen",
                true
        );
        categoria = new Categoria(
                2L,
                "Nombre categoria",
                "description"
        );
        PedidoPlato pedidoPlato = new PedidoPlato(
                null,
                plato,
                5
        );
        platos = new ArrayList<>();
        platos.add(pedidoPlato);
    }

    @Test
    void generarPedidoCasoExitoso(){
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.verificarPedidoCliente(any())).thenReturn(false);
        when(platoPersistencePort.verificarRestaurantePlato(any(),any())).thenReturn(true);
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(Optional.of(restaurante));
        when(pedidoPersistencePort.guardarPedido(any())).thenReturn(1L);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));

        pedidoUseCase.generarPedido(1L,platos);

        pedido = platos.get(0).getIdPedido();
        assertEquals("Pendiente",pedido.getEstado());
        assertEquals(restaurante,pedido.getIdRestaurante());
    }
    @Test
    void generarPedidoClientePedidoActivo(){
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.verificarPedidoCliente(any())).thenReturn(true);
        when(platoPersistencePort.verificarRestaurantePlato(any(),any())).thenReturn(true);
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(Optional.of(restaurante));
        when(pedidoPersistencePort.guardarPedido(any())).thenReturn(1L);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));

        assertThrows(ClientePedidoActivoException.class,()->pedidoUseCase.generarPedido(1L,platos));
    }
    @Test
    void generarPedidoPlatosDiferenteRestaurante(){
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.verificarPedidoCliente(any())).thenReturn(false);
        when(platoPersistencePort.verificarRestaurantePlato(any(),any())).thenReturn(false);
        when(restaurantePersistencePort.obtenerRestaurante(any())).thenReturn(Optional.of(restaurante));
        when(pedidoPersistencePort.guardarPedido(any())).thenReturn(1L);
        when(platoPersistencePort.obtenerPlato(any())).thenReturn(Optional.of(plato));

        assertThrows(PedidoPlatoDiferenteRestauranteException.class,()->pedidoUseCase.generarPedido(1L,platos));
    }
    @Test
    void obtenerPedidosPorEstadoCasoExitoso(){
        List<Pedido> pedidos = new ArrayList<>();
        pedido.setIdRestaurante(restaurante);
        pedido.setEstado("Pendiente");
        pedidos.add(pedido);
        Page<Pedido> pagina = new PageImpl<>(pedidos, PageRequest.of(0,10),10);
        Long idRestaurante = 1L;
        String estado = "Pendiente";
        int elementos = 10;
        int numPagina = 0;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerPedidos(idRestaurante,estado,elementos,numPagina)).thenReturn(pagina);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        Page<Pedido> respuesta = pedidoUseCase.obtenerPedidosPorEstado(idRestaurante,estado,elementos,numPagina);

        assertEquals(pagina,respuesta);
    }
    @Test
    void obtenerPedidosPorEstadoRolNoAutorizado(){
        List<Pedido> pedidos = new ArrayList<>();
        pedido.setIdRestaurante(restaurante);
        pedido.setEstado("Pendiente");
        pedidos.add(pedido);
        Page<Pedido> pagina = new PageImpl<>(pedidos, PageRequest.of(0,10),10);
        Long idRestaurante = 1L;
        String estado = "Pendiente";
        int elementos = 10;
        int numPagina = 0;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerPedidos(idRestaurante,estado,elementos,numPagina)).thenReturn(pagina);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        assertThrows(UsuarioNoAutorizadoException.class, ()-> pedidoUseCase.obtenerPedidosPorEstado(idRestaurante,estado,elementos,numPagina));
    }
    @Test
    void obtenerPEdidosPorEstadoEmpleadoOtroRestaurante(){
        List<Pedido> pedidos = new ArrayList<>();
        pedido.setIdRestaurante(restaurante);
        pedido.setEstado("Pendiente");
        pedidos.add(pedido);
        Page<Pedido> pagina = new PageImpl<>(pedidos, PageRequest.of(0,10),10);
        Long idRestaurante = 1L;
        String estado = "Pendiente";
        int elementos = 10;
        int numPagina = 0;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerPedidos(idRestaurante,estado,elementos,numPagina)).thenReturn(pagina);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(false);

        assertThrows(EmpleadoDiferenteRestauranteException.class, ()-> pedidoUseCase.obtenerPedidosPorEstado(idRestaurante,estado,elementos,numPagina));
    }
    @Test
    void asignarPedidoCasoExitoso(){
        List<Long> pedidos = new ArrayList<>();
        pedidos.add(1L);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadRestaurantePedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");

        pedidoUseCase.asignarPedido(1L, pedidos);

        verify(pedidoPersistencePort).actualizarPedido(1L,Constantes.PEDIDO_EN_PREPARACION,1L);
    }
    @Test
    void asignarPedidoRolNoAutorizado(){
        List<Long> pedidos = new ArrayList<>();
        pedidos.add(1L);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadRestaurantePedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");

        assertThrows(UsuarioNoAutorizadoException.class,()->pedidoUseCase.asignarPedido(1L,pedidos));
    }
    @Test
    void asignarPedidoEstadoNoValido(){
        List<Long> pedidos = new ArrayList<>();
        pedidos.add(1L);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadRestaurantePedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(false);
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");

        assertThrows(PedidoEstadoNoValidoException.class,()->pedidoUseCase.asignarPedido(1L,pedidos));
    }
    @Test
    void asignarPedidoNoExistePedido(){
        List<Long> pedidos = new ArrayList<>();
        pedidos.add(1L);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(false);
        when(pedidoPersistencePort.validadRestaurantePedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");

        assertThrows(PedidoNoExisteException.class,()->pedidoUseCase.asignarPedido(1L,pedidos));
    }
    @Test
    void asignarPedidoEmpleadoOtroRestaurante(){
        List<Long> pedidos = new ArrayList<>();
        pedidos.add(1L);
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(false);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadRestaurantePedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");

        assertThrows(EmpleadoDiferenteRestauranteException.class,()->pedidoUseCase.asignarPedido(1L,pedidos));
    }
    @Test
    void marcarPedidoEntregadoCasoExitoso(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(pedidoPersistencePort.pedidoVerificarCodigo(1L,1818)).thenReturn(true);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        pedidoUseCase.marcarPedidoEntregado(idPedido,codigo);

        verify(pedidoPersistencePort).actualizarPedido(idPedido,Constantes.PEDIDO_ENTREGADO);
    }
    @Test
    void marcarPedidoEntregadoPedidoNoExiste(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(false);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(pedidoPersistencePort.pedidoVerificarCodigo(1L,1818)).thenReturn(true);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        assertThrows(PedidoNoExisteException.class,()->pedidoUseCase.marcarPedidoEntregado(idPedido,codigo));
    }
    @Test
    void marcarPedidoRolNoAutorizado(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(pedidoPersistencePort.pedidoVerificarCodigo(1L,1818)).thenReturn(true);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        assertThrows(UsuarioNoAutorizadoException.class,()->pedidoUseCase.marcarPedidoEntregado(idPedido,codigo));
    }
    @Test
    void marcarPedidoEmpleadoDiferenteRestaurante(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(pedidoPersistencePort.pedidoVerificarCodigo(1L,1818)).thenReturn(true);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(false);

        assertThrows(EmpleadoDiferenteRestauranteException.class,()->pedidoUseCase.marcarPedidoEntregado(idPedido,codigo));
    }
    @Test
    void marcarPedidoEstadoNoValido(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(false);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(pedidoPersistencePort.pedidoVerificarCodigo(1L,1818)).thenReturn(true);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        assertThrows(PedidoEstadoNoValidoException.class,()->pedidoUseCase.marcarPedidoEntregado(idPedido,codigo));
    }
    @Test
    void marcarPedidoCodigoVerificacionIncorrecto(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(pedidoPersistencePort.pedidoVerificarCodigo(1L,1818)).thenReturn(false);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);

        assertThrows(CodigoIncorrectoException.class,()->pedidoUseCase.marcarPedidoEntregado(idPedido,codigo));
    }
    @Test
    void marcarPedidoListoCasoExitoso(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(mensajeriaServicePort.enviarMensaje()).thenReturn(codigo);

        pedidoUseCase.marcarPedidoListo(idPedido);

        verify(pedidoPersistencePort).actualizarPedido(idPedido,"Listo");
        verify(pedidoPersistencePort).actualizarPedido(idPedido,codigo);
    }
    @Test
    void marcarPedidoNoExiste(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(false);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(mensajeriaServicePort.enviarMensaje()).thenReturn(codigo);

        assertThrows(PedidoNoExisteException.class,()->pedidoUseCase.marcarPedidoListo(idPedido));
    }
    @Test
    void marcarPedidoListoRolNoAutorizado(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn("ROLE_CLIENTE");
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(mensajeriaServicePort.enviarMensaje()).thenReturn(codigo);

        assertThrows(UsuarioNoAutorizadoException.class,()->pedidoUseCase.marcarPedidoListo(idPedido));
    }
    @Test
    void marcarPedidoListoEmpleadoOtroRestaurante(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(false);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(mensajeriaServicePort.enviarMensaje()).thenReturn(codigo);

        assertThrows(EmpleadoDiferenteRestauranteException.class,()->pedidoUseCase.marcarPedidoListo(idPedido));
    }
    @Test
    void marcarPedidoListoEstadoNoValido(){
        int codigo = 1818;
        Long idPedido = 1L;
        when(feignServicePort.obtenerRolFromToken(any())).thenReturn(Constantes.ROLE_EMPLEADO);
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validadEstadoPedido(any(),any())).thenReturn(false);
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.obtenerIdClienteFromPedido(any())).thenReturn(1L);
        when(emplRestPersistencePort.validarExistenciaEmpleadoRestaurante(any(),any())).thenReturn(true);
        when(feignServicePort.obtenerCorreoFromUsuario(any())).thenReturn("correo");
        when(mensajeriaServicePort.enviarMensaje()).thenReturn(codigo);

        assertThrows(PedidoEstadoNoValidoException.class,()->pedidoUseCase.marcarPedidoListo(idPedido));
    }
    @Test
    void cancelarPedidoCasoExitoso(){
        Long idPedido = 1L;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.validarPedidoUsuario(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.obtenerEstadoPedido(any())).thenReturn(Constantes.PEDIDO_PENDIENTE);

        String response = pedidoUseCase.cancelarPedido(idPedido);

        assertEquals("Pedido cancelado",response);
    }
    @Test
    void cancelarPedidoClientePedidoDiferente(){
        Long idPedido = 1L;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.validarPedidoUsuario(any(),any())).thenReturn(false);
        when(pedidoPersistencePort.obtenerEstadoPedido(any())).thenReturn(Constantes.PEDIDO_PENDIENTE);

        String response = pedidoUseCase.cancelarPedido(idPedido);

        assertEquals("El pedido que intenta cancelar no es suyo",response);
    }
    @Test
    void cancelarPedidoEstadoNoValido(){
        Long idPedido = 1L;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.validarPedidoUsuario(any(),any())).thenReturn(true);
        when(pedidoPersistencePort.obtenerEstadoPedido(any())).thenReturn(Constantes.PEDIDO_EN_PREPARACION);

        String response = pedidoUseCase.cancelarPedido(idPedido);

        assertEquals("El pedido no se puede cancelar porque se encuentra en estado: En preparacion",response);
    }
    @Test
    void obtenerLogsCasoExitoso(){
        LogPedido log = new LogPedido();
        List<LogPedido> logs = new ArrayList<>();
        logs.add(log);
        logs.add(log);
        logs.add(log);
        Long idPedido = 1L;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validarPedidoUsuario(any(),any())).thenReturn(true);
        when(trazabilidadServicePort.obtenerLogs(any())).thenReturn(logs);

        List<LogPedido> response = pedidoUseCase.obtenerLogsPedido(idPedido);

        assertEquals(logs,response);
    }
    @Test
    void obtenerLogsClientePedidoDiferente(){
        LogPedido log = new LogPedido();
        List<LogPedido> logs = new ArrayList<>();
        logs.add(log);
        logs.add(log);
        logs.add(log);
        Long idPedido = 1L;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(true);
        when(pedidoPersistencePort.validarPedidoUsuario(any(),any())).thenReturn(false);
        when(trazabilidadServicePort.obtenerLogs(any())).thenReturn(logs);

        assertThrows(UsuarioNoAutorizadoException.class, ()->pedidoUseCase.obtenerLogsPedido(idPedido));
    }
    @Test
    void obtenerLogsPedidoNoExiste(){
        LogPedido log = new LogPedido();
        List<LogPedido> logs = new ArrayList<>();
        logs.add(log);
        logs.add(log);
        logs.add(log);
        Long idPedido = 1L;
        when(feignServicePort.obtenerIdUsuarioFromToken(any())).thenReturn("1");
        when(pedidoPersistencePort.pedidoExiste(any())).thenReturn(false);
        when(pedidoPersistencePort.validarPedidoUsuario(any(),any())).thenReturn(true);
        when(trazabilidadServicePort.obtenerLogs(any())).thenReturn(logs);

        assertThrows(PedidoNoExisteException.class, ()->pedidoUseCase.obtenerLogsPedido(idPedido));
    }

}
