package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.EmplRestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmplRestRepository extends JpaRepository<EmplRestEntity,Long> {
    List<EmplRestEntity> findAllByIdRestaurante(Long idRestaurante);
    Boolean findByIdEmpleadoAndIdRestaurante (Long idEmpleado, Long idRestaurante);
}
