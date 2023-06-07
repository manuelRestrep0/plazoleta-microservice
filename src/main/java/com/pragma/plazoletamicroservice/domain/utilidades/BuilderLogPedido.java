package com.pragma.plazoletamicroservice.domain.utilidades;

import com.pragma.plazoletamicroservice.domain.model.LogPedido;

public class BuilderLogPedido extends LogPedido {

    public BuilderLogPedido() {
    }
    public void infoPedido(Long idPedido, String estadoAnterior, String estadoNuevo){
        setIdPedido(idPedido);
        setEstadoAnterior(estadoAnterior);
        setEstadoNuevo(estadoNuevo);
    }
    public void infoCliente(Long idCliente, String correoCliente){
        setIdCliente(idCliente);
        setCorreoCliente(correoCliente);
    }
    public void infoEmpleado(Long idEmpleado, String correoEmpleado){
        setIdEmpleado(idEmpleado);
        setCorreoEmpleado(correoEmpleado);
    }
}
