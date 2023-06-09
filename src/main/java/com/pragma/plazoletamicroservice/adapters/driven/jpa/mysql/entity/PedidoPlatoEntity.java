package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Pedidos_Platos")
@Getter
@Setter
public class PedidoPlatoEntity {

    @EmbeddedId
    private PedidoPlatoEntityPK id;
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @MapsId("idPedido")
    private PedidoEntity idPedido;
    @ManyToOne
    @JoinColumn(name = "id_plato")
    @MapsId("idPlato")
    private PlatoEntity idPlato;
    private Integer cantidad;
}
