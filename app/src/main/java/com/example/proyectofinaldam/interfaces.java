package com.example.proyectofinaldam;

import com.example.proyectofinaldam.Clases.Encuesta;
import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.Clases.Votaciones;

import java.util.ArrayList;
import java.util.List;

public interface interfaces {


    interface OnCheckFieldListener {
        void onFieldExists(boolean exists);
    }

    interface OnGetNextIdValueListener {
        void onGetNextIdValue(int i);
    }

    public interface OnGetIdUsuarioListener {
        void onGetIdUsuario(Integer idUsuario);

    }
    public interface OnGruposObtenidosListener {
        void onGruposObtenidos(ArrayList<Grupo> listaGrupos);


    }

    public class OnUsuarioActualizadoListener {
        public void onUsuarioActualizado() {
        }
    }

    public class OnUsuarioActualizadoBorradoListener {
        public void onUsuarioActualizado() {
        }
    }

    public interface OnEventosObtenidosListener { //Este es un arraylist de Eventos

        void onEventosObtenidos(ArrayList<Evento> listaEvento);
    }
    public interface OnEventoObtenidoListener { //interfaz para sacar un evento solo.
        void onEventoObtenido(Evento evento);

    }
    public interface onEncuestaObtenidaListener{

        void onEncuestaObtenida(Encuesta encuesta);
    }
    public interface onVotacionesObtenidaListener{
        void onVotacionesObtenida(Votaciones votaciones);
    }

    public interface OnIdsEventosObtenidosListener {
        void onIdsEventosObtenidos(List<Integer> idsEventos);
    }

    public interface OnColorGrupoObtenidoListener {
        void onColorGrupoObtenido(int color);
        void onColorGrupoObtenidoError();
    }

}

