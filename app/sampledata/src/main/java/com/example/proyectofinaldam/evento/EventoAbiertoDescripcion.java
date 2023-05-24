package com.example.proyectofinaldam.evento;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.proyectofinaldam.R;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class EventoAbiertoDescripcion extends AppCompatActivity {

        private Button btn_ConfirmacionA;
        private Button btn_VerVotacionesA;
        private Button btn_EncuestaA;
        private TextView tv_NombreVerEventoA;

        private TextView tv_DescripcionVerEventoA;
        private TextView et_FechaLimiteVotacion;
        private TextView tv_FechaLimiteVotacion;
        private TextView tv_DescripcionVerEvA;
        private TextView tv_GrupoVerEventoA;
        private Spinner sp_GrupoVerEventoA;
        private TextView et_NombreVerEventoA;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.evento_abierto_descripcion);


            btn_ConfirmacionA = findViewById(R.id.btn_confirmacionA);
            btn_VerVotacionesA = findViewById(R.id.btn_verVotaciones);
            btn_EncuestaA = findViewById(R.id.btn_encuesta);
            tv_NombreVerEventoA = findViewById(R.id.tv_nombre_verEventoA);
            tv_DescripcionVerEventoA = findViewById(R.id.et_descripcionVerEventoA);
            et_FechaLimiteVotacion = findViewById(R.id.et_fechaLimiteEncuesta);
            tv_FechaLimiteVotacion = findViewById(R.id.tv_fechaLimiteEncuesta);
            tv_DescripcionVerEvA = findViewById(R.id.tv_descripcionVerEvA);
            tv_GrupoVerEventoA = findViewById(R.id.tv_grupoVerEventoA);
            sp_GrupoVerEventoA = findViewById(R.id.sp_grupoVerEventoA);
            et_NombreVerEventoA = findViewById(R.id.et_nombre_verEventoA);

            btn_EncuestaA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent irEncuesta = new Intent(EventoAbiertoDescripcion.this,Encuesta.class);
                    startActivity(irEncuesta);
                }
            });
            btn_ConfirmacionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent irConfirmacion = new Intent(EventoAbiertoDescripcion.this,Confirmacion.class);
                    startActivity(irConfirmacion);
                }
            });
            btn_VerVotacionesA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent irVerVotaciones = new Intent(EventoAbiertoDescripcion.this,Votaciones.class);
                    startActivity(irVerVotaciones);
                }
            });

        }
    }

