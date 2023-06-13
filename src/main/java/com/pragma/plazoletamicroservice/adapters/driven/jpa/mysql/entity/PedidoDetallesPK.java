package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Embeddable
public class PedidoDetallesPK implements Serializable {
    private Long idPedido;
    private Long idPlato;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoDetallesPK that = (PedidoDetallesPK) o;
        return Objects.equals(idPedido, that.idPedido) && Objects.equals(idPlato, that.idPlato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idPlato);
    }
}
