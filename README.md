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

### /plato/agregar-plato         

Ejemplo de la petición:

```java {.highlight .highlight-source-java .bg-black}
   "nombre": "string",
  "idCategoria": Long,
  "descripcion": "string",
  "precio": "String numerico positivo",
  "idRestaurante": Long,
  "urlImagen": "string"
```

La respuesta:

```java {.highlight .highlight-source-java .bg-black}
{
	"mensaje": "El plato ha sido creado."
}
```


### /plato/modificar-plato            

Ejemplo de la petición:

```java {.highlight .highlight-source-java .bg-black}
  "id": Long,
  "precio": "String numerico positivo",
  "descripcion": "string"
```

La respuesta: 

```java {.highlight .highlight-source-java .bg-black}
{
	"mensaje": "El plato ha sido modificado"
}
```


### /plato/disponibilidad

En este endpoint se puede cambiar el estado de un plato para indicar si esta disponible o no disponible.
Se le provee el id del plato y el estado al que se quiere ubicar, true para indicar que esta disponible 
y false para indicar lo contrario.

Ejemplo de la peticion:

```java {.highlight .highlight-source-java .bg-black}
  "id": Long,
  "disponibilidad": boolean
```


### /restaurante/listar/{elementos}   GET

Este endpoint trae una lista de paginas de los restaurantes disponibles con su nombre y url. se le debe indicar el numero de elementos
por pagina.

### /plato/listar   GET

Este endpoint trae un lista de paginas de los platos de un restaurante. Se le debe indicar el nombre de la categoria del plato, el id del restaurante
y los elementos por pagina en el url. 

La categoria esta por defecto en all, para traer todos los platos indiferentemente de su categoria. Por lo que, si no se indica categoria, se 
traen todos los platos del restaurante.

Un ejemplo de la peticio es: 

```java {.highlight .highlight-source-java .bg-black}
/plato/listar?nombreCategoria=all&restaurante=11&elementos=4
```

### /pedido/generar-pedido   POST

Para generar un nuevo pedido debes estar logeado como cliente para verificar que no tengas pedidos activos.
El pedido recibe un body donde se le indica el id del restaurante al que se va a hacer el pedido y 
una lista de platos donde se indica el id del plato y la cantidad.

Ejemplo de la peticion: 

```java {.highlight .highlight-source-java .bg-black}
{
  "idRestaurante": 7,
  "platos": [
    {
      "idPlato": 11,
      "cantidad": 1
    },{
      "idPlato": 12,
      "cantidad": 1
    },{
      "idPlato": 13,
      "cantidad": 1
    },
  ]
}

```

### /pedido/obtener-pedidos  GET

En este endpoint se obtienen una lista de paginas de los pedidos filtrado por estado. 
En el url debes especificar el id del restaurante, el estado y la cantidad de elementos por pagina.

Ejemplo de la peticion:
```java {.highlight .highlight-source-java .bg-black}
/pedido/obtener-pedidos?idRestaurante=11&estado=Pendiente&elementos=5'
```

### /pedido/asignar-pedido  PATCH

Para utilizar este endpoint debes estar logeado como empleado.

El endpoint recibe un body que contiene el id del restaurante y el id de los pedidos que se va a asignar el empleado que 
se encuentre logeado.
Los pedidos deben ser del mismo restaurante, si no coincide que todos los pedidos tengan el mismo restaurante se lanza un error.

Ejemplo de la peticion:

```java {.highlight .highlight-source-java .bg-black}
{
  "idRestaurante": 7,
  "pedidos": [
    7,
    8,
    9,
    10
  ]
}
```



