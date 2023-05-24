package com.example.proyectofinaldam.Clases;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    Integer id;
    String nombre;
    String descripcion;
    Integer tipo;
    String fecha;
    Integer fk_idGrupo;
    Integer confirmacion;

    List<Integer> siConfirmados = new ArrayList<>();
    List<Integer> noConfirmados = new ArrayList<>();


    public Evento(Integer id, String nombre, String descripcion, Integer tipo, String fecha, Integer confirmacion, Integer fk_idGrupo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.confirmacion = confirmacion;
        this.fk_idGrupo = fk_idGrupo;
    }
    public Evento(String nombre, String descripcion, Integer tipo, String fecha, Integer fk_idGrupo, Integer confirmacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.fk_idGrupo = fk_idGrupo;
        this.confirmacion = confirmacion;
    }

    public Evento(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getFk_idGrupo() {
        return fk_idGrupo;
    }

    public void setFk_idGrupo(Integer fk_idGrupo) {
        this.fk_idGrupo = fk_idGrupo;
    }

    public Integer getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(Integer confirmacion) {
        this.confirmacion = confirmacion;
    }

    public List<Integer> getSiConfirmados() {
        return siConfirmados;
    }

    public void setSiConfirmados(List<Integer> siConfirmados) {
        this.siConfirmados = siConfirmados;
    }

    public List<Integer> getNoConfirmados() {
        return noConfirmados;
    }

    public void setNoConfirmados(List<Integer> noConfirmados) {
        this.noConfirmados = noConfirmados;
    }
}
