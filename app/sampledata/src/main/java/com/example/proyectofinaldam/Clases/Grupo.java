package com.example.proyectofinaldam.Clases;

import java.util.List;

public class Grupo {
    private Integer id;
    private String nombre;
    private String color;

    private String descripcion;
    private String administrador;
    private List<Integer> miembros;

   // private List<Integer> eventos;
    private String creacion;

    public Grupo(Integer id, String nombre, String descripcion, String color, String administrador, List<Integer> miembros, String creacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = color;
        this.administrador = administrador;
        this.miembros = miembros;
        //this.eventos = eventos;
        this.creacion = creacion;
    }
    public Grupo(String nombre, String descripcion, String color, List<Integer> miembros){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = color;
        this.miembros=miembros;
    }

    public Grupo(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    public Grupo(Integer id, String grupoNombre, String grupoColor) {
        this.id = id;
        this.nombre = grupoNombre;
        this.color = grupoColor;


    }

    // getters y setters para cada atributo

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public List<Integer> getMiembros() {
        return this.miembros;
    }

    public void setMiembros(List<Integer> miembros) {
        this.miembros = miembros;
    }

   /* public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }*/

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
