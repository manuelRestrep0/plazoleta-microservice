package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.EmplRestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmplRestRepository extends JpaRepository<EmplRestEntity,Long> {

}
