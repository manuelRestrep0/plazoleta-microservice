package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
public class PedidoPlatoEntityPK implements Serializable {
    private Long idPedido;
    private Long idPlato;
}
