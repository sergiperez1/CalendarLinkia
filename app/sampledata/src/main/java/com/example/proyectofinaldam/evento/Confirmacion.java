package com.example.proyectofinaldam.evento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;

public class Confirmacion extends AppCompatActivity {

    private TextView nombreEvento;
    private TextView fechaEvento;

    private RadioGroup opcionesRadioGroup;
    private RadioButton siRadioButton;
    private RadioButton noRadioButton;
    private Button confirmarButton;



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

        // Establecer el nombre del evento y la fecha en las vistas correspondientes de la bbdd
       /* nombreEventoTextView.setText("Evento de prueba");
        fechaEventoTextView.setText("10 de mayo de 2023");*/

        // Establecer la lógica del botón de confirmación.
        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irDesplDerecho = new Intent(Confirmacion.this, PantallaPrincipal.class);
                if (siRadioButton.isChecked()) {

                    // Acción a tomar si el usuario selecciona "Sí"
                    startActivity(irDesplDerecho);

                } else if (noRadioButton.isChecked()) {
                    // Acción a tomar si el usuario selecciona "No"
                    startActivity(irDesplDerecho);
                }
            }
        });
    }
}