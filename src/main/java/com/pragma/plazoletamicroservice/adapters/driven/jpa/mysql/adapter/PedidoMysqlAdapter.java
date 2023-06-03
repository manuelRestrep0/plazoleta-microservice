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
    public List<Page<Pedido>> obtenerPedidos(Long id, String estado, int elementos) {
        List<Page<Pedido>> paginas = new ArrayList<>();
        int numeroPagina = 0;
        Page<Pedido> pagina;
        do{
            Pageable pageable = PageRequest.of(numeroPagina,elementos);
            pagina = pedidoRepository.findAllByIdRestaurante_IdAndEstado(id,estado,pageable).map(pedidoEntityMapper::toPedido);
            paginas.add(pagina);
            numeroPagina++;
        } while (pagina.hasNext());
        return paginas;
    }

    @Override
    public Pedido obtenerPedido(Long id) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        return pedidoEntityMapper.toPedido(pedido.get());
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
    public boolean validadRestaurantePedido(Long idRestaurante, Long idPedido) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(idPedido);
        return pedido.filter(pedidoEntity -> idRestaurante.equals(pedidoEntity.getIdRestaurante().getId())).isPresent();
    }

    @Override
    public boolean validadEstadoPedido(Long id, String estado) {
        return pedidoRepository.existsByIdAndEstado(id,estado);
    }
}
