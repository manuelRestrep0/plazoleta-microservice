package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PedidoPlatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoPlatoRepository extends JpaRepository<PedidoPlatoEntity,Long> {
}
