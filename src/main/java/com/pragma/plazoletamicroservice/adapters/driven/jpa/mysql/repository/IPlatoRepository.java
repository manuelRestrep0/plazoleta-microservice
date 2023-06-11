package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IPlatoRepository extends JpaRepository<PlatoEntity, Long> {
    Page<PlatoEntity>  findAllByIdRestaurante_Id(Long id, Pageable pageable);
    Page<PlatoEntity> findAllByIdCategoria_NombreAndIdRestaurante_Id(String nombreCategoria, Long id, Pageable pageable);
    Boolean existsByIdAndIdRestaurante(Long idPlato, Long idRestaurante);

}
