package com.example.proyectofinaldam.Clases;

public class Encuesta {
    Integer id;
    Integer finalizada;
    String fechaLimite;
    String fechaFinal;
    String opcion1;
    String opcion2;
    String opcion3;
    Integer fk_idEvento;

    public Encuesta() {
    }
    //id, finalizada, fechaFinal, fechaLimite, opcion1, opcion2, opcion3, fk_IdEvento
    public Encuesta(Integer id, Integer finalizada, String fechaFinal, String fechaLimite, String opcion1, String opcion2, String opcion3, Integer fk_idEvento) {
        this.id = id;
        this.finalizada = finalizada;
        this.fechaLimite = fechaLimite;
        this.fechaFinal = fechaFinal;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.fk_idEvento = fk_idEvento;
    }

    public Encuesta(Integer finalizada, String fechaFinal, String fechaLimite, String opcion1, String opcion2, String opcion3, Integer fk_idEvento) {
        this.finalizada = finalizada;
        this.fechaLimite = fechaLimite;
        this.fechaFinal = fechaFinal;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.fk_idEvento = fk_idEvento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Integer finalizada) {
        this.finalizada = finalizada;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public Integer getFk_idEvento() {
        return fk_idEvento;
    }

    public void setFk_idEvento(Integer fk_idEvento) {
        this.fk_idEvento = fk_idEvento;
    }

}


