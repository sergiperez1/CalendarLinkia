package com.example.proyectofinaldam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectofinaldam.desplegables.BlankFragmentDesplDerecho;
import com.example.proyectofinaldam.desplegables.BlankFragmentDesplIzquierdo;
import com.example.proyectofinaldam.evento.CrearEvento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PantallaPrincipal extends AppCompatActivity {

    private Button btn_despl_izquierdo;
    private Button btn_despl_derecho;
    private FloatingActionButton btn_calendario_crear_evento;


    public Boolean mostrarBotones = true;

    int idUsuarioIniciado;
    int idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        // Obtenemos el idUsuario pasado desde la actividad anterior
        idUsuarioIniciado = getIntent().getIntExtra("idUsuario", idUsuario);


        btn_despl_izquierdo = (Button) findViewById(R.id.btn_despl_izquierdo);
        btn_despl_derecho = (Button) findViewById(R.id.btn_despl_derecho);
        btn_calendario_crear_evento = (FloatingActionButton) findViewById(R.id.btn_calendario_crear_evento);

    }

    public void LlamadaDesplIzquierdo(View view){
        //Para pasar el IdUsuario
        Bundle args = new Bundle();
        args.putInt("idUsuario", idUsuarioIniciado);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlankFragmentDesplIzquierdo fragment = new BlankFragmentDesplIzquierdo();
        //Para pasar el idusuario.
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.container1, fragment);
        fragmentTransaction.commit();

        btn_calendario_crear_evento.setVisibility(View.GONE);
        btn_despl_izquierdo.setVisibility(View.GONE);
        btn_despl_derecho.setVisibility(View.GONE);

    }

    // Nos lleva a la pantalla de crear evento
    public void LlamadaCrearEvento(View view){

        Intent intentCrearEvento = new Intent(this, CrearEvento.class);
        intentCrearEvento.putExtra("idUsuario", idUsuarioIniciado);
        startActivity(intentCrearEvento);

    }

    public void LlamadaDesplDerecho(View view){
        Bundle args = new Bundle();
        args.putInt("idUsuario", idUsuarioIniciado);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlankFragmentDesplDerecho fragmentderecho = new BlankFragmentDesplDerecho();
        //Para pasar el idusuario.
        fragmentderecho.setArguments(args);
        fragmentTransaction.replace(R.id.container2, fragmentderecho);
        fragmentTransaction.commit();

        mostrarBotones = false;

        btn_calendario_crear_evento.setVisibility(View.GONE);
        btn_despl_izquierdo.setVisibility(View.GONE);
        btn_despl_derecho.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container2);
        Fragment fragment2 = fragmentManager.findFragmentById(R.id.container1);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();

            // Mostrar los botones de la actividad principal
            mostrarBotones = true;
            btn_calendario_crear_evento.setVisibility(View.VISIBLE);
            btn_despl_izquierdo.setVisibility(View.VISIBLE);
            btn_despl_derecho.setVisibility(View.VISIBLE);

        }else {
            super.onBackPressed();
        }

    }

}