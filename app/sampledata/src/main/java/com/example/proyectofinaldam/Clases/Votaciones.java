package com.example.proyectofinaldam.Clases;

import java.util.ArrayList;
import java.util.List;

public class Votaciones {
    Integer id;
    Integer recuento1;
    Integer recuento2;
    Integer recuento3;
    List<Integer> usuariosVotados = new ArrayList<>();
    Integer fk_idencuesta;

    public Votaciones(Integer id, Integer recuento1, Integer recuento2, Integer recuento3, List<Integer> usuariosVotados, Integer fk_idencuesta) {
        this.id = id;
        this.recuento1 = recuento1;
        this.recuento2 = recuento2;
        this.recuento3 = recuento3;
        this.usuariosVotados = usuariosVotados;
        this.fk_idencuesta = fk_idencuesta;
    }

    public Votaciones(Integer recuento1, Integer recuento2, Integer recuento3, List<Integer> usuariosVotados, Integer fk_idencuesta) {
        this.recuento1 = recuento1;
        this.recuento2 = recuento2;
        this.recuento3 = recuento3;
        this.usuariosVotados = usuariosVotados;
        this.fk_idencuesta = fk_idencuesta;
    }

    public List<Integer> getUsuariosVotados() {
        return usuariosVotados;
    }

    public void setUsuariosVotados(List<Integer> usuariosVotados) {
        this.usuariosVotados = usuariosVotados;
    }

    public Votaciones() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecuento1() {
        return recuento1;
    }

    public void setRecuento1(Integer recuento1) {
        this.recuento1 = recuento1;
    }

    public Integer getRecuento2() {
        return recuento2;
    }

    public void setRecuento2(Integer recuento2) {
        this.recuento2 = recuento2;
    }

    public Integer getRecuento3() {
        return recuento3;
    }

    public void setRecuento3(Integer recuento3) {
        this.recuento3 = recuento3;
    }

    public Integer getFk_idencuesta() {
        return fk_idencuesta;
    }

    public void setFk_idencuesta(Integer fk_idencuesta) {
        this.fk_idencuesta = fk_idencuesta;
    }
}

