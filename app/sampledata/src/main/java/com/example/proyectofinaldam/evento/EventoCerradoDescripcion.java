package com.example.proyectofinaldam.evento;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.R;

public class EventoCerradoDescripcion extends AppCompatActivity {

    private Button btn_ConfirmacionC;
    private TextView tv_NombreVerEventoC;
    private TextView tv_DescripcionVerEventoC;
    private TextView tv_FechaEventoC;
    private TextView tv_FechaEventoTitulo;
    private TextView tv_DescripcionVerEvC;
    private TextView tv_GrupoVerEventoC;
    private Spinner sp_GrupoVerEventoC;

    private TextView tv_Name_verEventoC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_cerrado_descripcion);


        btn_ConfirmacionC = findViewById(R.id.btn_confirmacionC);
        tv_NombreVerEventoC = findViewById(R.id.tv_nombre_verEventoC);
        tv_Name_verEventoC = findViewById(R.id.tv_Name_verEventoC);
        tv_DescripcionVerEventoC = findViewById(R.id.tv_descripcionVerEvC);
        tv_FechaEventoC = findViewById(R.id.tv_fechaEventoC);
        tv_FechaEventoTitulo = findViewById(R.id.tv_fechaEvento);
        tv_DescripcionVerEvC = findViewById(R.id.tv_descripcionVerEvC);
        tv_GrupoVerEventoC = findViewById(R.id.tv_grupoVerEventoC);
        sp_GrupoVerEventoC = findViewById(R.id.sp_grupoVerEventoC);



        btn_ConfirmacionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irConfirmacion = new Intent(EventoCerradoDescripcion.this,Confirmacion.class);
                startActivity(irConfirmacion);
            }
        });
    }
}
