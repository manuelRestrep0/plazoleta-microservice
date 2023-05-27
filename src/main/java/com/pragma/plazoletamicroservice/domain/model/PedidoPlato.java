package com.pragma.plazoletamicroservice.domain.model;

public class PedidoPlato {

    private Pedido idPedido;
    private Plato idPlato;
    private Integer cantidad;

    public PedidoPlato() {
    }

    public PedidoPlato(Pedido idPedido, Plato idPlato, Integer cantidad) {
        this.idPedido = idPedido;
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }

    public Plato getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Plato idPlato) {
        this.idPlato = idPlato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
