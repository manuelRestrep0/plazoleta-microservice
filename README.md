# plazoleta-microservicio
Este microservicio se encarga de las consultas que se hagan con la base de datos de la plazoleta y la logica referente a esta.
Consume a la api de usuario-microservicio para validar informacion de los usuarios.

El puerto del microservicio es el 8090 y recuerde modificar los datos de acceso a la base de datos.

URL swagger: [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html)

## Endpoints

### /restaurante/crear

Recuerde que: 
<ol>
<li>El nombre del restaurante puede tener numeros, sin embargo no puede ser solo numerico.</li>
<li>El nit debe ser solo numerico y es un campo unico.</li>
<li>El numero de telefono puede tener un '+' al inicio y debe ser solo numerico. Debe contener entre 6 y 13 caracteres</li>
<li>El url del Logo debe ser una url valida.</li>
<li>El id del propietario es un Long y debe estar previamente registrado en la base de datos de usuarios.</li>
</ol>


Ejemplo de la petición: 

```java {.highlight .highlight-source-java .bg-black}
    "nombre": "KFC",
    "direccion": "string",
    "telefono": "565658829",
    "urlLogo": "string",
    "nit": "090753917520215751697114404281119104133159921789696073224297711926",
    "idPropietario": 1
```

La respuesta es un codigo 201 con el siguiente mensaje:

```java {.highlight .highlight-source-java .bg-black}
{
	"mensaje": "Se ha creado el restaurante."
}
```
