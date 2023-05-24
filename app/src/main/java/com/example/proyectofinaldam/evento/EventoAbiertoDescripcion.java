package com.example.proyectofinaldam.evento;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Encuesta;
import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.Clases.Votaciones;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.gestion_grupos.VerGrupo;
import com.example.proyectofinaldam.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventoAbiertoDescripcion extends AppCompatActivity {

        private Button btn_ConfirmacionA;

        private Button btn_EncuestaA;
        private TextView tv_NombreVerEventoA;

        private TextView tv_descripcionEvAb;
        private TextView tv_FechaLimiteAb;
        private TextView tv_FechaLimiteVotacion;
        private TextView tv_DescripcionVerEvA;
        private TextView tv_GrupoVerEventoA;
        private TextView tv_grupoEvAb;
        private TextView tv_NombreEvAb;

        int idUsuarioEventoA;
        int idUsuarioIntent;
        private String nombreEvento;

        int fk_idGrupoEventoA;
        int fk_idGrupoIntent;
        int idEventoA;
        int idEventoIntent;

        String fechaFinalEncuesta;
        String fechaLimiteText;
        boolean diaComprobado;
        String nameEvento;


        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.evento_abierto_descripcion);


            btn_ConfirmacionA = findViewById(R.id.btn_confirmacionA);
            btn_EncuestaA = findViewById(R.id.btn_encuesta);
            tv_NombreVerEventoA = findViewById(R.id.tv_nombre_verEventoA);
            tv_descripcionEvAb = findViewById(R.id.tv_descripcionEvAb);
            tv_FechaLimiteAb = findViewById(R.id.tv_FechaLimiteEvAb);
            tv_FechaLimiteVotacion = findViewById(R.id.tv_fechaLimiteEncuesta);
            tv_DescripcionVerEvA = findViewById(R.id.tv_descripcionVerEvA);
            tv_GrupoVerEventoA = findViewById(R.id.tv_grupoVerEventoA);
            tv_grupoEvAb = findViewById(R.id.tv_grupoEvAb);
            tv_NombreEvAb = findViewById(R.id.tv_NombreEvAb);


            //Recogemos los valores del intent.

            idUsuarioEventoA = getIntent().getIntExtra("idUsuario", idUsuarioIntent);
            Log.d("EventoAbierto", "id Usuario es " + idUsuarioEventoA);
            nombreEvento = getIntent().getStringExtra("nomEvento");
            Log.d("EventoAbierto", "Nombre es: " + nombreEvento);
            fk_idGrupoEventoA = getIntent().getIntExtra("idGrupo", fk_idGrupoIntent);
            Log.d("EventoAbierto", "id Grupo es " + fk_idGrupoEventoA);
            idEventoA = getIntent().getIntExtra("idEvento", idEventoIntent);
            Log.d("EventoAbierto", "id Evento es " + idEventoA);

            //Pasamos los datos a los textView.
            tv_NombreEvAb.setText(nombreEvento);

            //Obtenemos el nombre del grupo.
            ConexionBDD.obtenerNombreGrupoPorId(fk_idGrupoEventoA, new VerGrupo.OnGrupoObtenidoListener() {
                @Override
                public void onGrupoObtenido(Grupo grupo) {
                    tv_grupoEvAb.setText(grupo.getNombre());

                }
            });
            //obtenemos los datos del evento.
            ConexionBDD.obtenerEventosPorId(idEventoA, new interfaces.OnEventoObtenidoListener() {
                @Override
                public void onEventoObtenido(Evento evento) {
                    tv_descripcionEvAb.setText(evento.getDescripcion());
                    nameEvento = evento.getNombre();

                    ConexionBDD.obtenerEncuestasPorId(idEventoA, new interfaces.onEncuestaObtenidaListener() {
                        @Override
                        public void onEncuestaObtenida(Encuesta encuesta) {
                            tv_FechaLimiteAb.setText(encuesta.getFechaLimite());
                            Log.d("Descrip Abierto", "La fecha limite es " + tv_FechaLimiteAb.getText());
                            //Comprobar el dia
                            diaComprobado = comprobarFechaEvento();

                            if(diaComprobado){
                                ConexionBDD.obtenerGrupoPorId(evento.getFk_idGrupo(), new VerGrupo.OnGrupoObtenidoListener() {
                                    @Override
                                    public void onGrupoObtenido(Grupo grupo) {
                                        List<Integer> miembros = grupo.getMiembros();
                                        for (int i = 0; i  < miembros.size(); i++) {
                                            Integer idU= miembros.get(i);
                                            ConexionBDD.mostrarNotificacion(getApplicationContext(), idU,"Recordatorio Evento", "Ya puedes confirmar asistencia en el evento, " + evento.getNombre());
                                            Log.d("Notificar", "Se ha notificado a: " + idU);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });

            btn_EncuestaA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent irEncuesta = new Intent(EventoAbiertoDescripcion.this, Encuesta_pantalla.class);
                    irEncuesta.putExtra("idUsuario", idUsuarioEventoA);
                    irEncuesta.putExtra("idEvento", idEventoA);
                    startActivity(irEncuesta);
                }
            });


            btn_ConfirmacionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        if (diaComprobado) {
                            ConexionBDD.obtenerEncuestasPorId(idEventoA, new interfaces.onEncuestaObtenidaListener() {
                                @Override
                                public void onEncuestaObtenida(Encuesta encuesta) {
                                    int fk_idEncuesta = encuesta.getId();
                                    //Funcion para obtener la opcion mas votada. Le pasamos el id del evento.
                                    ConexionBDD.obtenerVotacionPorId(fk_idEncuesta, new interfaces.onVotacionesObtenidaListener() {
                                        @Override
                                        public void onVotacionesObtenida(Votaciones votaciones) {
                                            Integer opcionMasVotada = votaciones.getOpcionMasVotada();
                                            if (opcionMasVotada == 1) {
                                                encuesta.setFechaFinal(encuesta.getOpcion1());
                                                fechaFinalEncuesta = encuesta.getFechaFinal();
                                            } else if (opcionMasVotada == 2) {
                                                encuesta.setFechaFinal(encuesta.getOpcion2());
                                                fechaFinalEncuesta = encuesta.getFechaFinal();
                                            } else if (opcionMasVotada == 3) {
                                                encuesta.setFechaFinal(encuesta.getOpcion3());
                                                fechaFinalEncuesta = encuesta.getFechaFinal();
                                            }
                                            //Inicializamos encuesta y la actualizamos.
                                            ConexionBDD.actualizarFechaFinalEncuesta(fk_idEncuesta, fechaFinalEncuesta);
                                            ConexionBDD.actualizarEventoAbiertoFinal(encuesta.getFk_idEvento(), fechaFinalEncuesta);
                                            Intent irConfirmacion = new Intent(EventoAbiertoDescripcion.this, Confirmacion.class);
                                            irConfirmacion.putExtra("idUsuario", idUsuarioEventoA);
                                            irConfirmacion.putExtra("idEvento", idEventoA);
                                            startActivity(irConfirmacion);
                                        }
                                    });
                                }
                            });
                        }else{
                                Toast.makeText(getApplicationContext(), "Aún no es la fecha limite.", Toast.LENGTH_LONG).show();
                            }

                }
            });

        }//Fin oncreate

            // Método para obtener la fecha actual en el formato deseado
            public boolean comprobarFechaEvento() {
                fechaLimiteText = tv_FechaLimiteAb.getText().toString();
                // Crear un SimpleDateFormat con el formato adecuado para tu fecha
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                boolean diaPosterior = false;
                try {
                    Date fechaLimite = dateFormat.parse(fechaLimiteText);
                    Date fechaActual = Calendar.getInstance().getTime();
                    if (fechaActual.after(fechaLimite)) {
                        diaPosterior = true;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return diaPosterior;
            }


            }//Fin clase

