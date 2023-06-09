package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EmpleadosRestaurante")
@Getter
@Setter
public class EmplRestEntity {
    @Id
    private Long idEmpleado;
    private Long idRestaurante;
}
