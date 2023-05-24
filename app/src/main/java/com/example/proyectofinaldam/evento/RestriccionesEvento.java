package com.example.proyectofinaldam.evento;

public class RestriccionesEvento {
    private CrearEvento crearEvento;

    public RestriccionesEvento() {
    }


    //Todas las restricciones de evento.

    /**
     * Crear evento.
     * 1-Nombre(ni numeros ni caracteres especiales, la longitud tendra limite 30 caracteres).
     * 2-Descripcion(maximo 250 caracteres).
     * 3-Fecha limite(Formato xx/xx/xxxx.  No se pueda poner fecha limite en el pasado).
     * 4-Fecha abierta SI (No puedas dejarte la fecha limite vacia. Esto se aplicaria al darle al boton de crear.).
     * 5- Opciones de dia de evento ( Que no este la fecha vacia y que sea valida y posterior a la fecha limite).
     */


    //El objetivo de este metodo es poner restricciones al nombre. Si hay restricciones nos devolvera false y si no hay true.
    //Hacemos un integer marcandole 0 al principio. Le pasamos los ifs de las restricciones que queremos.
    //Si no hay restriccion -> 0 . Si la restriccion es de campo vacio -> 1. Si la restriccion es de nº o caracteres especiales -> 2.
    public Integer restriccionNombreCrearEv(String nombreEvento){

        Integer nombreValido = 0;


        //El if se cumplirá si el nombre tiene numeros o caracteres especiales o si el campo esta vacio
        if(nombreEvento.length() == 0){

            nombreValido = 1;

        }
        else if (!nombreEvento.matches("^[a-zA-Z ]*$")){

            nombreValido = 2;

        }

        return nombreValido;


    }












}
