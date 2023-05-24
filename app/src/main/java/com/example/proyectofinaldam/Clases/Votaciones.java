package com.example.proyectofinaldam.Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Votaciones {
    Integer id;
    Integer recuento1;
    Integer recuento2;
    Integer recuento3;
    List<Integer> usuariosVotados = new ArrayList<>();
    Integer fk_idencuesta;

    public Votaciones(Integer id) {
        this.id = id;
    }

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

    public Integer getOpcionMasVotada() {
        Map<Integer, Integer> recuentos = new HashMap<>();
        recuentos.put(1, recuento1);
        recuentos.put(2, recuento2);
        recuentos.put(3, recuento3);

        Integer opcionMasVotada = null;
        Integer maxVotos = 0;

        for (Map.Entry<Integer, Integer> entry : recuentos.entrySet()) {
            Integer opcion = entry.getKey();
            Integer votos = entry.getValue();

            if (votos > maxVotos) {
                opcionMasVotada = opcion;
                maxVotos = votos;
            }
        }

        return opcionMasVotada;
    }
}

