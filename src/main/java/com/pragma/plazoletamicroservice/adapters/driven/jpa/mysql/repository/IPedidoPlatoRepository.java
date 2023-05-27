package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoPlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoPlatoRepository extends JpaRepository<PedidoPlatoEntity,Long> {

    Page<PedidoPlatoEntity> findAllByIdPedido_IdRestaurante_IdAndAndIdPedido_Estado(Long id, String estado, Pageable pageable);
}
