package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPedidoRepository extends JpaRepository<PedidoEntity,Long> {

    Optional<PedidoEntity> findByIdCliente(Long idCliente);
    Boolean existsByIdCliente(Long idCliente);
    Page<PedidoEntity> findAllByIdRestaurante_IdAndAndEstado(Long id, String estado, Pageable pageable);
}
