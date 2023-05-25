package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Pedidos_Platos")
@Getter
@Setter
public class PedidoPlato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private PedidoEntity idPedido;
    @ManyToOne
    private PlatoEntity idPlato;
    private Integer cantidad;
}
