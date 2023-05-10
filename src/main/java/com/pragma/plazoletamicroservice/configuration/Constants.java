package com.pragma.plazoletamicroservice.configuration;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String RESPONSE_MESSAGE_KEY = "mensaje";
    public static final String CREACION_EXITOSA_RESTAURANTE = "Se ha creado el restaurante.";
    public static final String NOMBRE_RESTAURANTE_MAL_FORMATO = "El nombre del restaurante no puede ser solo numerico.";
    public static final String NIT_YA_REGISTRADO = "El nit de este restaurante ya se encuentra registrado.";
    public static final String USUARIO_NO_PROPIETARIO = "El id del usuario no corresponde a un propietario.";
    public static final String RESTAURANTE_NO_ENCONTRADO = "El restaurante no se encuentra registrado.";
    public static final String PLATO_MODIFICADO = "El plato ha sido modificado";
    public static final String PLATO_CREADO = "El plato ha sido creado.";
    public static final String PLATO_NO_REGISTRADO = "El plato que intenta modificar no se encuentra registrado";
    public static final String CATEGORIA_NO_ENCONTRADA = "La categoria no se encuentra registrada.";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
