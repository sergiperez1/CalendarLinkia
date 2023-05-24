package com.example.proyectofinaldam.evento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Encuesta;
import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.interfaces;

import java.util.ArrayList;
import java.util.List;

public class Encuesta_pantalla extends AppCompatActivity {

    private TextView tv_Encuesta_evento;
    private TextView tv_Encuesta_fecha;
    private RadioButton rb_opcion1;
    private TextView tv_1;

    private boolean rb1Selected = false;
    int votoOpc1;
    private RadioButton rb_opcion2;
    private TextView tv_2;
    private boolean rb2Selected = false;
    int votoOpc2;
    private RadioButton rb_opcion3;
    private TextView tv_3;
    private boolean rb3Selected = false;
    int votoOpc3;
    private Button votarEncuesta;

    int idUsuarioEncuesta;
    int idUsuarioIntent;
    int idEventoEncuesta;
    int idEventoIntent;

    int fk_idEncuesta;

    private List<Integer> usuarioVotado = new ArrayList<>(); //Se usa para guardar los miembros de las votaciones.
    boolean aVotado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta);

        // Configurar las vistas
        tv_Encuesta_evento = findViewById(R.id.tv_nombreEvento);
        tv_Encuesta_fecha = findViewById(R.id.tv_fechaEncuesta);
        tv_1 = findViewById(R.id.tv_resultadoVotacion1);
        tv_2 = findViewById(R.id.tv_resultadoVotacion2);
        tv_3 = findViewById(R.id.tv_resultadoVotacion3);

        rb_opcion1 = findViewById(R.id.rb_opcion1);
        votoOpc1 = 0;
        votoOpc2 = 0;
        votoOpc3 = 0;
        rb_opcion2 = findViewById(R.id.rb_opcion2);
        rb_opcion3 = findViewById(R.id.rb_opcion3);
        votarEncuesta = findViewById(R.id.rb_votarEncuesta);
        aVotado = false;

        idUsuarioEncuesta = getIntent().getIntExtra("idUsuario", idUsuarioIntent); Log.d("Encuesta", "id Usuario es " + idUsuarioEncuesta);
        idEventoEncuesta = getIntent().getIntExtra("idEvento", idEventoIntent); Log.d("Encuesta", "id Evento es " + idEventoEncuesta);

        //Mostramos los datos y los añadimos en los textview.
        ConexionBDD.obtenerEventosPorId(idEventoEncuesta, new interfaces.OnEventoObtenidoListener() {
            @Override
            public void onEventoObtenido(Evento evento) {
                tv_Encuesta_evento.setText(evento.getNombre());
                String Desc = tv_Encuesta_evento.getText().toString();
                Log.d("Encuesta", "el nombre es:  " + Desc);

            }
        });
        ConexionBDD.obtenerEncuestasPorId(idEventoEncuesta, new interfaces.onEncuestaObtenidaListener() {
            @Override
            public void onEncuestaObtenida(Encuesta encuesta) {
                tv_Encuesta_fecha.setText(encuesta.getFechaLimite());
                String Desc1 = tv_Encuesta_fecha.getText().toString();
                Log.d("Encuesta", "La fecha limite es:  " + Desc1);
                Log.d("Encuesta", "La fecha final es: " + encuesta.getFechaFinal());
                fk_idEncuesta = encuesta.getId();  Log.d("Encuesta", "La foreign key de encuesta es: " + fk_idEncuesta);



                if (encuesta.getOpcion1() == null){
                    rb_opcion1.setVisibility(View.INVISIBLE);
                    tv_1.setVisibility(View.INVISIBLE);
                } else{
                    rb_opcion1.setText(encuesta.getOpcion1());

                }
                if (encuesta.getOpcion2() == null){
                    rb_opcion2.setVisibility(View.INVISIBLE);
                    tv_2.setVisibility(View.INVISIBLE);
                } else{
                    rb_opcion2.setText(encuesta.getOpcion2());
                }
                if (encuesta.getOpcion3() == null){
                    rb_opcion3.setVisibility(View.INVISIBLE);
                    tv_3.setVisibility(View.INVISIBLE);
                } else{
                    rb_opcion3.setText(encuesta.getOpcion3());
                }

                ConexionBDD.obtenerVotacionPorId(fk_idEncuesta, new interfaces.onVotacionesObtenidaListener() {
                    @Override
                    public void onVotacionesObtenida(com.example.proyectofinaldam.Clases.Votaciones votaciones) {
                        String uno = votaciones.getRecuento1().toString();
                        String dos = votaciones.getRecuento2().toString();
                        String tres = votaciones.getRecuento3().toString();
                        tv_1.setText(uno);
                        tv_2.setText(dos);
                        tv_3.setText(tres);
                        Log.d("Votacion", "El recuento 1 es:  " + uno);
                        Log.d("votacion", "Recuento 1 " + votaciones.getRecuento1());
                        usuarioVotado = votaciones.getUsuariosVotados();

                        for (Integer votado : usuarioVotado) {
                            if (votado != null && votado == idUsuarioEncuesta) {
                                aVotado = true; //Usuario no ha votado
                            }
                        }
                        // Configurar el botón votarEncuesta con un OnClickListener
                        votarEncuesta.setOnClickListener(new View.OnClickListener() { //Poner un if de idusuario. para que solo vote 1 vez.
                            @Override
                            public void onClick(View view) {
                                if (!aVotado) {
                                    // Aquí manejas el evento de clic del botón.
                                    //Radiobutton seleccionados.
                                    rb1Selected = rb_opcion1.isChecked();
                                    if (rb1Selected) {
                                        // El RadioButton está seleccionado
                                        Log.d("Encuesta", "RadioButton1 seleccionado");
                                        votoOpc1 = 1;
                                    } else {
                                        // El RadioButton no está seleccionado
                                        Log.d("Encuesta", "RadioButton1 no seleccionado");
                                        votoOpc1 = 0;
                                    }
                                    rb2Selected = rb_opcion2.isChecked();
                                    if (rb2Selected) {
                                        // El RadioButton está seleccionado
                                        Log.d("Encuesta", "RadioButton2 seleccionado");
                                        votoOpc2 = 1;
                                    } else {
                                        // El RadioButton no está seleccionado
                                        Log.d("Encuesta", "RadioButton2 no seleccionado");
                                        votoOpc2 = 0;
                                    }
                                    rb3Selected = rb_opcion3.isChecked();
                                    if (rb3Selected) {
                                        // El RadioButton está seleccionado
                                        Log.d("Encuesta", "RadioButton3 seleccionado");
                                        votoOpc3 = 1;
                                    } else {
                                        // El RadioButton no está seleccionado
                                        Log.d("Encuesta", "RadioButton3 no seleccionado");
                                        votoOpc3 = 0;
                                    }

                                    usuarioVotado.add(idUsuarioEncuesta);
                                    ConexionBDD.actualizarVotacion(fk_idEncuesta, votoOpc1, votoOpc2, votoOpc3, usuarioVotado); Log.d("updateVot", "" + fk_idEncuesta + votoOpc1 + votoOpc2 + votoOpc3 + usuarioVotado);
                                    Toast.makeText(getApplicationContext(), "¡Votación exitosa!", Toast.LENGTH_SHORT).show();

                                    Intent pantPrincipal = new Intent(Encuesta_pantalla.this, PantallaPrincipal.class);
                                    pantPrincipal.putExtra("idUsuario", idUsuarioEncuesta);
                                    startActivity(pantPrincipal);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Ya has votado esta encuesta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                });

            }
        });





    } //Oncreate.
}