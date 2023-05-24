package com.example.proyectofinaldam.evento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.gestion_grupos.VerGrupo;
import com.example.proyectofinaldam.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Confirmacion extends AppCompatActivity {

    private TextView nombreEvento;
    private TextView fechaEvento;

    private RadioGroup opcionesRadioGroup;
    private RadioButton siRadioButton;
    private RadioButton noRadioButton;
    private Button confirmarButton;

    int idUsuarioConfirmacion;
    int idEventoConfirmacion;

    int idUsuarioIntent;
    int idEventoIntent;

    List<Integer> siConfirmados = new ArrayList<>();
    List<Integer> noConfirmados = new ArrayList<>();
    boolean diaComprobado;
    boolean diaAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmacion);

        // Obtener referencias a las vistas del layout
        nombreEvento = findViewById(R.id.tv_NombreEventoConfirmacion);
        fechaEvento = findViewById(R.id.tv_FechaEventoConfirmacion);
        opcionesRadioGroup = findViewById(R.id.rdGroup_siNo);
        siRadioButton = findViewById(R.id.rb_si);
        noRadioButton = findViewById(R.id.rb_no);
        confirmarButton = findViewById(R.id.confirmarButton);


        idUsuarioConfirmacion = getIntent().getIntExtra("idUsuario", idUsuarioIntent);
        Log.d("Confirmacion", "id Usuario es " + idUsuarioConfirmacion);
        idEventoConfirmacion = getIntent().getIntExtra("idEvento", idEventoIntent);
        Log.d("Confirmacion", "id Evento es " + idEventoConfirmacion);


        //obtenemos los datos del evento.
        ConexionBDD.obtenerEventosPorIdConfirmacion(idEventoConfirmacion, new interfaces.OnEventoObtenidoListener() {
            @Override
            public void onEventoObtenido(Evento evento) {
                nombreEvento.setText(evento.getNombre());
                fechaEvento.setText(evento.getFecha());
                siConfirmados = evento.getSiConfirmados();
                Log.d("Confirmacion", "Si confirmados " + siConfirmados);
                noConfirmados = evento.getNoConfirmados();
                Log.d("Confirmacion", "No confirmados " + noConfirmados);

                diaComprobado = comprobarFechaEvento();
                diaAnterior = comprobarFechaEventoAnterior();

                if(diaAnterior){ //Notifica cuando se acabe el evento.
                    ConexionBDD.obtenerEventosPorId(idEventoConfirmacion, new interfaces.OnEventoObtenidoListener() {
                        @Override
                        public void onEventoObtenido(Evento evento) {
                            int fk = evento.getFk_idGrupo();
                            ConexionBDD.obtenerGrupoPorId(fk, new VerGrupo.OnGrupoObtenidoListener() {
                                @Override
                                public void onGrupoObtenido(Grupo grupo) {
                                    List<Integer> miembros = grupo.getMiembros();
                                    for (int i = 0; i  < miembros.size(); i++) {
                                        Integer idU= miembros.get(i);
                                        ConexionBDD.mostrarNotificacion(getApplicationContext(), idU,"Recordatorio Evento", "Mañana tienes el evento " + evento.getNombre() +" en el grupo: " + grupo.getNombre());
                                        Log.d("Notificar", "Se ha notificado a: " + idU);
                                    }
                                }
                            });
                        }
                    });

                }
                // Establecer la lógica del botón de confirmación.
                confirmarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent irDesplDerecho = new Intent(Confirmacion.this, PantallaPrincipal.class);

                        int idUsuario = idUsuarioConfirmacion;
                        if (diaComprobado) { //Si fecha actual es anterior a diaEvento puedes votar confirmar.
                            if (siRadioButton.isChecked()) {
                                if (!siConfirmados.contains(idUsuario)) {
                                    siConfirmados.add(idUsuario);
                                    noConfirmados.remove(Integer.valueOf(idUsuario));
                                    ConexionBDD.actualizarCampoConfirmacion(idEventoConfirmacion, siConfirmados, noConfirmados);
                                    Log.d("Confirmacion", "confirmados " + siConfirmados);
                                    Toast.makeText(getApplicationContext(), "Has confirmado si con éxito", Toast.LENGTH_SHORT).show();
                                    irDesplDerecho.putExtra("idUsuario", idUsuarioConfirmacion);
                                    startActivity(irDesplDerecho);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Ya has votado que si, no puedes volver a votar si", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (noRadioButton.isChecked()) {
                                if (!noConfirmados.contains(idUsuario)) {
                                    noConfirmados.add(idUsuario);
                                    siConfirmados.remove(Integer.valueOf(idUsuario));
                                    ConexionBDD.actualizarCampoConfirmacion(idEventoConfirmacion, siConfirmados, noConfirmados);
                                    Toast.makeText(getApplicationContext(), "Has confirmado con éxito no", Toast.LENGTH_SHORT).show();
                                    irDesplDerecho.putExtra("idUsuario", idUsuarioConfirmacion);
                                    startActivity(irDesplDerecho);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Ya has votado que no, no puedes volver a votar no", Toast.LENGTH_SHORT).show();
                                }
                            } //if no
                        } else {
                            ConexionBDD.actualizarCampoFinalizado(idEventoConfirmacion, 1); //finalizado 1 = evento finalizado
                            Toast.makeText(getApplicationContext(), "Evento ya finalizado.", Toast.LENGTH_LONG).show();
                            //Notificar evento
                        }
                    } // onclick
                });//btn
            } //Oneventoobtenido
        });


    } //Oncreate

    // Método para obtener la fecha actual en el formato deseado
    public boolean comprobarFechaEvento() {
        String fecha = fechaEvento.getText().toString();
        // Crear un SimpleDateFormat con el formato adecuado para tu fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        boolean diaAnterior = false;
        try {
            Date fechaDelEvento = dateFormat.parse(fecha);
            Date fechaActual = Calendar.getInstance().getTime();
            if (fechaActual.before(fechaDelEvento)) {
                diaAnterior = true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return diaAnterior;
    }
    public boolean comprobarFechaEventoAnterior() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date fechaActualAnterior = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String fechaEventoStr = fechaEvento.getText().toString();

        boolean diaAnterior = false;
        try {
            Date fechaDelEvento = dateFormat.parse(fechaEventoStr);
            if (fechaDelEvento.equals(fechaActualAnterior)) {
                diaAnterior = true;
            }
        } catch (ParseException e) {
            // Manejar el error de análisis de fecha
            e.printStackTrace();
            // Podrías mostrar un mensaje de error o retornar un valor predeterminado
        }
        return diaAnterior;
    }


}
