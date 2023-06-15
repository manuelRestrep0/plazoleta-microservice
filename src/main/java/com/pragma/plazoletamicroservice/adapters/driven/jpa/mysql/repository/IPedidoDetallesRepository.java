package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoDetallesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoDetallesRepository extends JpaRepository<PedidoDetallesEntity,Long> {
}
