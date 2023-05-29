package com.pragma.plazoletamicroservice.configuration;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String RESPONSE_MESSAGE_KEY = "mensaje";
    public static final String ROLE_ADMINISTRADOR = "ROLE_ADMINISTRADOR";
    public static final String ROLE_PROPIETARIO = "ROLE_PROPIETARIO";
    public static final String ROLE_EMPLEADO = "ROLE_EMPLEADO";
    public static final String CREACION_EXITOSA_RESTAURANTE = "Se ha creado el restaurante.";
    public static final String NIT_YA_REGISTRADO = "El nit de este restaurante ya se encuentra registrado.";
    public static final String USUARIO_NO_PROPIETARIO = "El id del usuario no corresponde a un propietario.";
    public static final String RESTAURANTE_NO_ENCONTRADO = "El restaurante no se encuentra registrado.";
    public static final String PLATO_MODIFICADO = "El plato ha sido modificado";
    public static final String PLATO_CREADO = "El plato ha sido creado.";
    public static final String PEDIDO_CREADO = "El pedido ha sido realizado con existo.";
    public static final String PEDIDO_MODIFICADO = "El pedido ha sido modificado.";
    public static final String PEDIDOS_DIFERENTES_RESTAURANTES = "no coincide con el restaurante del empleado";
    public static final String PEDIDO_ASIGNADO = "El pedido ha sido asignado";
    public static final String PEDIDO_NO_REGISTRADO = "El pedido no se encuentra registrado";
    public static final String CLIENTE_PEDIDO_ACTIVO = "El cliente tiene un pedido activo";
    public static final String PEDIDO_PENDIENTE = "Pendiente";
    public static final String PEDIDO_EN_PREPARACION = "En preparacion";
    public static final String PEDIDO_LISTO = "Listo";
    public static final String PEDIDO_ENTREGADO = "Entregado";
    public static final String PEDIDO_CANCELADO = "Cancelado";
    public static final String PLATO_NO_REGISTRADO = "El plato no se encuentra registrado";
    public static final String CATEGORIA_NO_ENCONTRADA = "La categoria no se encuentra registrada.";
    public static final String PROPIETARIO_DIFERENTE = "El id del propietario no corresponde al id del propietario de este restaurante";
    public static final String USUARIO_NO_AUTORIZADO = "El usuario no tiene permisos para realizar esta peticion.";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
