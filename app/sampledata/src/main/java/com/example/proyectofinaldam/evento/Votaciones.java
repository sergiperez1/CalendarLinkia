package com.example.proyectofinaldam.evento;



import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.R;

public class Votaciones extends AppCompatActivity {


    private TextView tv_votaciones;

    private TextView tv_opcion1;
    private TextView tv_opcion2;
    private TextView tv_opcion3;
    private TextView tv_resultado1;
    private TextView tv_resultado2;
    private TextView tv_resultado3;
    //Inicializamos las variables del layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votaciones);

        tv_votaciones = findViewById(R.id.tv_Votaciones);
        tv_opcion1 = findViewById(R.id.tv_opcion1votacion);
        tv_opcion2 = findViewById(R.id.tv_opcion2votacion);
        tv_opcion3 = findViewById(R.id.tv_opcion3votacion);
        tv_resultado1 = findViewById(R.id.tv_resultadoVotacion1);
        tv_resultado2= findViewById(R.id.tv_resultadoVotacion2);
        tv_resultado3 = findViewById(R.id.tv_resultadoVotacion3);

        //Falta el grafico de votaciones.
    }
}