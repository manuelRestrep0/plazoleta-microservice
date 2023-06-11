package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoPlatoEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPedidoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPedidoPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPedidoPlatoRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPedidoRepository;
import com.pragma.plazoletamicroservice.domain.model.Pedido;
import com.pragma.plazoletamicroservice.domain.model.PedidoPlato;
import com.pragma.plazoletamicroservice.domain.spi.IPedidoPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PedidoMysqlAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;
    private final IPedidoPlatoRepository pedidoPlatoRepository;
    private final IPedidoPlatoEntityMapper pedidoPlatoEntityMapper;

    @Override
    public void guardarPedido(Pedido pedido, List<PedidoPlato> platosPedidos) {
        PedidoEntity pedidoEntity = pedidoEntityMapper.toEntity(pedido);
        pedidoRepository.save(pedidoEntity);
        List<PedidoPlatoEntity> platosEntity = new ArrayList<>();
        platosPedidos.forEach(pedidoPlato -> platosEntity.add(pedidoPlatoEntityMapper.toEntity(pedidoPlato)));
        platosEntity.forEach(pedidoPlatoEntity -> pedidoPlatoEntity.setIdPedido(pedidoRepository.findById(pedidoEntity.getId()).get()));
        pedidoPlatoRepository.saveAll(platosEntity);
    }
    @Override
    public Boolean verificarPedidoCliente(Long idCliente) {
        return pedidoRepository.existsByIdCliente(idCliente);
    }
    @Override
    public Page<Pedido> obtenerPedidos(Long id, String estado, int elementos, int numeroPagina) {
        Pageable pageable = PageRequest.of(numeroPagina,elementos);
        return pedidoRepository.findAllByIdRestaurante_IdAndEstado(id,estado,pageable).map(pedidoEntityMapper::toPedido);
    }
    @Override
    public boolean pedidoExiste(Long id) {
        return pedidoRepository.existsById(id);
    }
    @Override
    public boolean pedidoVerificarCodigo(Long id, Integer codigo) {
        return pedidoRepository.existsByIdAndCodigoVerificacion(id,codigo);
    }
    @Override
    public void actualizarPedido(Long idPedido, String estado, Long idChef) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        if(pedido.isPresent()){
            PedidoEntity pedidoEntity = pedido.get();
            pedidoEntity.setEstado(estado);
            pedidoEntity.setIdChef(idChef);
            pedidoRepository.save(pedidoEntity);
        }
    }
    @Override
    public void actualizarPedido(Long idPedido, String estado){
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        if(pedido.isPresent()){
            PedidoEntity pedidoEntity = pedido.get();
            pedidoEntity.setEstado(estado);
            pedidoRepository.save(pedidoEntity);
        }
    }
    @Override
    public void actualizarPedido(Long idPedido, Integer codigoVerificacion) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        if(pedido.isPresent()){
            PedidoEntity pedidoEntity = pedido.get();
            pedidoEntity.setCodigoVerificacion(codigoVerificacion);
            pedidoRepository.save(pedidoEntity);
        }
    }
    @Override
    public boolean validadRestaurantePedido(Long idRestaurante, Long idPedido) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        return pedido.filter(pedidoEntity -> idRestaurante.equals(pedidoEntity.getIdRestaurante().getId())).isPresent();
    }
    @Override
    public boolean validadEstadoPedido(Long id, String estado) {
        return pedidoRepository.existsByIdAndEstado(id,estado);
    }
    @Override
    public boolean validarPedidoUsuario(Long id, Long idCliente) {
        return pedidoRepository.existsByIdAndIdCliente(id, idCliente);
    }
    @Override
    public String obtenerEstadoPedido(Long id) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        String estado = "";
        if(pedido.isPresent()){
            estado = pedido.get().getEstado();
        }
        return estado;
    }
    @Override
    public Long obtenerIdClienteFromPedido(Long idPedido) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        Long idCliente = null;
        if(pedido.isPresent()){
            idCliente = pedido.get().getIdCliente();
        }
        return idCliente;
    }

    @Override
    public Long obtenerIdRestauranteFromPedido(Long idPedido) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        Long idRestaurante = null;
        if(pedido.isPresent()){
            idRestaurante = pedido.get().getIdRestaurante().getId();
        }
        return idRestaurante;
    }

    @Override
    public List<Pedido> obtenerPedidosFromRestaurante(Long idRestaurante) {
        return pedidoRepository.findAllByIdRestaurante(idRestaurante).stream().map(pedidoEntityMapper::toPedido).toList();
    }
}
