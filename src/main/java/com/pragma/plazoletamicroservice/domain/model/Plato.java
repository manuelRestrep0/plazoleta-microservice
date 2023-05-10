package com.pragma.plazoletamicroservice.domain.model;

public class Plato {
    private Long id;
    private String nombre;
    private Categoria idCategoria;
    private Long idCategoriaAux;
    private String descripcion;
    private String precio;
    private Restaurante idRestaurante;
    private Long idRestauranteAux;
    private String urlImagen;
    private Boolean activo;

    public Plato() {
    }

    public Plato(Long id, String nombre, Categoria idCategoria, String descripcion, String precio, Restaurante idRestaurante, String urlImagen, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idRestaurante = idRestaurante;
        this.urlImagen = urlImagen;
        this.activo = activo;
    }

    public Long getIdCategoriaAux() {
        return idCategoriaAux;
    }

    public void setIdCategoriaAux(Long idCategoriaAux) {
        this.idCategoriaAux = idCategoriaAux;
    }

    public Long getIdRestauranteAux() {
        return idRestauranteAux;
    }

    public void setIdRestauranteAux(Long idRestauranteAux) {
        this.idRestauranteAux = idRestauranteAux;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Restaurante getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Restaurante idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
