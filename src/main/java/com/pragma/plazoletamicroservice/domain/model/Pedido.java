package com.pragma.plazoletamicroservice.domain.model;

import java.time.LocalDate;

public class Pedido {
    private Long idCliente;
    private LocalDate fecha;
    private String estado;
    private Long idChef;
    private Restaurante idRestaurante;

    public Pedido() {
    }

    public Pedido(Long idCliente, LocalDate fecha, String estado, Long idChef, Restaurante idRestaurante) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
        this.idChef = idChef;
        this.idRestaurante = idRestaurante;
    }

    public Restaurante getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Restaurante idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdChef() {
        return idChef;
    }

    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }

}
