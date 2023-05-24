package com.example.proyectofinaldam.evento;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.gestion_grupos.VerGrupo;
import com.example.proyectofinaldam.interfaces;

public class EventoCerradoDescripcion extends AppCompatActivity {

    private Button btn_ConfirmacionC;

    private TextView tv_NombreVerEventoC;
    private TextView tv_FechaEventoC;
    private TextView tv_DescripcionVerEventoC;
    private TextView tv_GrupoEventoCerrado;

    //Titulos
    private TextView tv_TFechaEventoTitulo;
    private TextView tv_NombreEventoT;
    private TextView tv_fechaEventoT;
    private TextView tv_DescripcionVerEvCT;
    private TextView tv_GrupoVerEventoCT;



    int idUsuarioEventoC;
    int idUsuarioIntent;
    private String nombreEvento;

    int fk_idGrupoEventoC;
    int fk_idGrupoIntent;
    int idEventoC;
    int idEventoIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_cerrado_descripcion);


        //Titulos. Las variables con "T" es porque son titulos.
        tv_TFechaEventoTitulo = findViewById(R.id.tv_fechaEventoT);
        tv_NombreEventoT = findViewById(R.id.tv_nombreEventoT);
        tv_GrupoVerEventoCT = findViewById(R.id.tv_grupoEventoT);
        tv_DescripcionVerEvCT = findViewById(R.id.tv_descripcionEventoT);
        tv_fechaEventoT = findViewById(R.id.tv_fechaEventoT);



        //Contenido del evento cerrado

        tv_NombreVerEventoC = findViewById(R.id.tv_nombre_verEventoC);
        tv_FechaEventoC = findViewById(R.id.tv_fechaEventoC);
        tv_GrupoEventoCerrado = findViewById(R.id.tv_grupoEvCerrado);
        tv_DescripcionVerEventoC = findViewById(R.id.tv_descripcionVerEventoC);



        idUsuarioEventoC = getIntent().getIntExtra("idUsuario", idUsuarioIntent); Log.d("EventoCerrado", "id Usuario es " + idUsuarioEventoC);
        nombreEvento = getIntent().getStringExtra("nomEvento"); Log.d("EventoCerrado", "Nombre es: " + nombreEvento);
        fk_idGrupoEventoC = getIntent().getIntExtra("idGrupo", fk_idGrupoIntent); Log.d("EventoCerrado", "id Grupo es " + fk_idGrupoEventoC);
        idEventoC = getIntent().getIntExtra("idEvento", idEventoIntent); Log.d("EventoCerrado", "id Evento es " + idEventoC);

        //Boton
        btn_ConfirmacionC = findViewById(R.id.btn_confirmacionC);

        tv_NombreVerEventoC.setText(nombreEvento);


        //Obtenemos el nombre del grupo.
        ConexionBDD.obtenerNombreGrupoPorId(fk_idGrupoEventoC, new VerGrupo.OnGrupoObtenidoListener() {
            @Override
            public void onGrupoObtenido(Grupo grupo) {
                tv_GrupoEventoCerrado.setText(grupo.getNombre());

            }
        });
        //obtenemos los datos del evento.
        ConexionBDD.obtenerEventosPorId(idEventoC, new interfaces.OnEventoObtenidoListener() {
            @Override
            public void onEventoObtenido(Evento evento) {
                tv_DescripcionVerEventoC.setText(evento.getDescripcion());
                tv_FechaEventoC.setText(evento.getFecha());
            }

        });


        btn_ConfirmacionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irConfirmacion = new Intent(EventoCerradoDescripcion.this,Confirmacion.class);
                irConfirmacion.putExtra("idUsuario", idUsuarioEventoC);
                irConfirmacion.putExtra("idEvento", idEventoC);
                startActivity(irConfirmacion);
            }
        });
    }

}//fin archivo
