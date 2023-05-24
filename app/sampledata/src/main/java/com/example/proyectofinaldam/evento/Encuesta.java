package com.example.proyectofinaldam.evento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;

public class Encuesta extends AppCompatActivity {

    private TextView tv_Encuesta_evento;
    private TextView tv_Encuesta_fecha;
    private RadioButton rb_opcion1;
    private RadioButton rb_opcion2;
    private RadioButton rb_opcion3;
    private Button votarEncuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta);

        // Configurar las vistas
        tv_Encuesta_evento = findViewById(R.id.tv_nombreEvento);
        tv_Encuesta_fecha = findViewById(R.id.tv_fechaEncuesta);
        rb_opcion1 = findViewById(R.id.rb_opcion1);
        rb_opcion2 = findViewById(R.id.rb_opcion2);
        rb_opcion3 = findViewById(R.id.rb_opcion3);
        votarEncuesta = findViewById(R.id.rb_votarEncuesta);

        // Configurar el botón votarEncuesta con un OnClickListener
        votarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí manejas el evento de clic del botón.
                /**
                 * pregunta para el chat: En java tengo un boton y 3 radiobutton que pueden ser seleccionados los 3. Se llaman opcion1, opcion 2 y opcion 3.  dentro de la funcion SetOnClickListener quiero que crees una funcion que recoja que radiobutton son seleccionados.
                 */
                Intent irDesplDerecho = new Intent(Encuesta.this, PantallaPrincipal.class);
                startActivity(irDesplDerecho);
            }
        });
    }


}