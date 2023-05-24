package com.example.proyectofinaldam.Clases;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String password;

    public Usuario(int id, String nombre, String correo, String password) {
        this.correo = correo;
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    // Getters and setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
