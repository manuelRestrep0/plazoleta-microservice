package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<PedidoEntity,Long> {
    Boolean existsByIdCliente(Long idCliente);
    Boolean existsByIdClienteAndEstado(Long idCliente, String estado);
    boolean existsByIdAndEstado(Long id, String estado);
    boolean existsByIdAndCodigoVerificacion(Long id, Integer codigo);
    boolean existsByIdAndIdCliente(Long id, Long idCliente);
    Page<PedidoEntity> findAllByIdRestaurante_IdAndEstado(Long id, String estado, Pageable pageable);
    List<PedidoEntity> findAllByIdRestaurante_Id(Long idRestaurante);
}
