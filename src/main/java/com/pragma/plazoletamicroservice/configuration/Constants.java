package com.pragma.plazoletamicroservice.configuration;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String RESPONSE_MESSAGE_KEY = "mensaje";
    public static final String CREACION_EXITOSA_RESTAURANTE = "Se ha creado el restaurante.";
    public static final String PLATO_MODIFICADO = "El plato ha sido modificado";
    public static final String PLATO_CREADO = "El plato ha sido creado.";
    public static final String PEDIDO_CREADO = "El pedido ha sido realizado con existo.";
    public static final String PEDIDO_MODIFICADO = "El pedido ha sido modificado.";
    public static final String PEDIDO_ASIGNADO = "El pedido ha sido asignado";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
