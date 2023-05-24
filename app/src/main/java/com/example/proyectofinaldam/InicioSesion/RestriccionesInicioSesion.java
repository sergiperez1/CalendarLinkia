package com.example.proyectofinaldam.InicioSesion;

import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.interfaces;

public class RestriccionesInicioSesion {
    static Integer usuarioValido = 0;
    static Integer passwordValida = 3;

    static Integer correoValido=3 ;

    static ConexionBDD conexionBDD = new ConexionBDD();


    /**
     * Pantalla inicio sesion.
     * 1- Usuario:
     * no este vacio y exista en la base de datos.
     * 2- Contraseña.
     * no este vacio y exista en la base de datos.
     * Esto ira en el boton de inicio sesion.
     */

    /**
     * Funcion ValidarUsuario.
     * El usuario pasa el nombre en el edittext donde iniciará sesion. Si al apretar el boton de iniciar no ha escrito el nombre el valor será 1 y aparecerá que no ha indicado nada.
     * Si ha indicado el nombre y este esta en la base de datos el valor será 2 y accederá a la app.
     *
     * @param usuario
     * @return
     */
    public static Integer validarUsuario(String usuario) {

        if (usuario.isEmpty()) {
            usuarioValido = 1;
        } else {

            conexionBDD.comprobarNombre("Usuarios", "Nombre", usuario, new interfaces.OnCheckFieldListener() {
                @Override
                public void onFieldExists(boolean exists) {
                    if (exists) {
                        // El nombre de usuario existe en la base de datos
                        usuarioValido = 0;
                    } else {
                        // El nombre de usuario no existe en la base de datos
                        usuarioValido = 2;
                    }
                }
            });
        }
        return usuarioValido;
    }

    public static Integer validarPassword(String password) {


        if (password.isEmpty()) {
            passwordValida = 1;
        } else {
            //COMPROBACION SI ESTA EN LA BDD
            conexionBDD.comprobarContrasena("Usuarios", "Password", password, new interfaces.OnCheckFieldListener() {
                @Override
                public void onFieldExists(boolean exists) {
                    if (exists) {
                        // El nombre de usuario existe en la base de datos
                        passwordValida = 0;
                    } else {
                        // El nombre de usuario no existe en la base de datos
                        passwordValida = 2;
                    }
                }
            });
        }
        return passwordValida;
    }


    /**
     * Pantalla Registro.
     * 1- Correo Electronico.
     * Correo requerido(no vacio) limite de 60 caracteres. formate con @, tenga .com .es .cat .net .edu .gov .org  -  no caracter raros.
     * 2- Usuario.
     * limite de caracteres 15. Sin espacios. sin caracteres raros. Usuario requerido(no vacio)
     * 3- Contraseña.
     * limite de caracteres 15. Sin espacios. 1 Mayuscula y 1 numero. Contraseña requerida(no vacio).
     */
    //Este validar correo se usa en registro
    public Integer validarCorreo(String correo) {
        Integer correoValido = 0;
        if (correo.isEmpty()) {
            correoValido = 1;
        } else if ((!correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|es|cat|net|edu|gov|org)$"))) {
            correoValido = 2;
        }

        return correoValido;
    }

    //Funcion para validar el correo de un usuario que no se acuerda la contraseña
    public Integer validarCorreoRecuperar(String correo) {

        if (correo.isEmpty()) {
            correoValido = 1;
        }else {//COMPROBACION SI ESTA EN LA BDD
            conexionBDD.comprobarCorreo("Usuarios", "Correo", correo, new interfaces.OnCheckFieldListener() {
                @Override
                public void onFieldExists(boolean exists) {
                    if (exists) {
                        // El nombre de usuario existe en la base de datos
                        correoValido = 0;
                    } else {
                        // El nombre de usuario no existe en la base de datos
                        correoValido = 2;
                    }
                }
            });

        }
        return correoValido;

    }

    public Integer validarUsuarioRegistro(String usuario) {

        Integer usuarioValido = 0;

        if (usuario.isEmpty()) {
            usuarioValido = 1;

        } else if (usuario.contains(" ")) { // no espacios vacios
            usuarioValido = 3;
        } else if (!usuario.matches("^[a-zA-Z0-9]*$")) { //caracteres especiales
            usuarioValido = 4;
        }
        return usuarioValido;
    }


    public Integer validarPasswordRegistro(String password) {

        Integer passwordValida = 0;

        if (password.isEmpty()) {
            passwordValida = 1;

        } else if (password.contains(" ")) { //--> no puede tener espacios
            passwordValida = 3; //
        } else if (!password.matches(".*\\d.*")) {//--> // Debe contener al menos un número
            passwordValida = 4;
        } else if (!password.matches(".*[A-Z].*")) {//--> // Debe contener al menos una mayuscula
            passwordValida = 5;
        }

        return passwordValida;
    }


    /**
     * Pantalla Recuperar password.
     * 1- Correo Electronico.
     * Que el correo exista en la bbdd, Correo requerido(no vacio) limite de 60 caracteres. formate con @, tenga .com .es .cat .net .edu .gov .org  -  no caracter raros.
     *
     */


    /**
     * Pantalla validar contraseña.
     * 1- codigo.
     * 5 caracteres obligatorios. Letras y numeros.
     */




    /**
     * Pantalla generar nueva contraseña.
     * 3- Contraseña.
     * limite de caracteres 15. Sin espacios. 1 Mayuscula y 1 numero. Contraseña requerida(no vacio).
     * <p>
     * Y que las dos edittext sean iguales.
     */


    public Integer validarPasswordNueva(String password) {

        Integer passwordValida = 0;

        if (password.isEmpty()) {
            passwordValida = 1;
        } else if (password.contains(" ")) { //--> no puede tener espacios
            passwordValida = 2; //
        } else if (!password.matches(".*\\d.*")) {//--> // Debe contener al menos un número
            passwordValida = 3;
        } else if (!password.matches(".*[A-Z].*")) {//--> // Debe contener al menos una mayuscula
            passwordValida = 4;
        }

        return passwordValida;
    }
}
    //