package com.example.proyectofinaldam.InicioSesion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;

public class GenerarNuevaPassword extends AppCompatActivity {
    private TextView tv_generarPassword;
    private EditText et_nuevaPassword1;
    private EditText et_nuevaPassword2;
    private Button btn_enviarPassword;
    private TextView tv_textoErroresPasswordNueva1;
    private TextView tv_asteriscoErroresPasswordNueva1;
    private TextView tv_asteriscoErroresPasswordNueva2;

    private String correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_password);

        correo = getIntent().getStringExtra("correo");
        Log.d("NuevaPassword", "Correo del usuario: " + correo);

        tv_generarPassword = findViewById(R.id.tv_generarPassword);
        et_nuevaPassword1 =  findViewById(R.id.et_nuevaPassword1);
        et_nuevaPassword2 = findViewById(R.id.et_nuevaPassword2);
        btn_enviarPassword = findViewById(R.id.btn_enviarNuevaPassword);
        tv_textoErroresPasswordNueva1 = findViewById(R.id.tv_textoErroresPasswordNueva);
        tv_asteriscoErroresPasswordNueva1 = findViewById(R.id.tv_asteriscoPasswordNueva1);
        tv_asteriscoErroresPasswordNueva2 = findViewById(R.id.tv_asteriscoPasswordNueva2);


        RestriccionesInicioSesion restriccionesInicioSesion = new RestriccionesInicioSesion();

        //Boton que al darle comprobará que las contraseñas cumplen con los requisitos y al darle a enviar vuelve al login
        btn_enviarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un boolean para la igualdadPassword, lo ponemos en false al inicio.
                //Pasamos el contenido del editText a String. Aplicamos el metodo para validar password nueva y el return lo guardamos en estadoPassword1
                boolean igualdadPassword = false;
                String passwordRecuperar1 = et_nuevaPassword1.getText().toString();

                Integer estadoPassword1 = restriccionesInicioSesion.validarPasswordNueva(passwordRecuperar1);
                //Si el estadoPassword1 es 0 es correcto, ponemos en errores invisibles
                if(estadoPassword1 == 0){
                    tv_textoErroresPasswordNueva1.setVisibility(View.INVISIBLE);
                    tv_asteriscoErroresPasswordNueva1.setVisibility(View.INVISIBLE);

                }else{
                    switch (estadoPassword1) {
                        //Si devuelve 1 es porque el campo esta vacio.
                        case 1:

                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*Campo contraseña requerido");
                            break;
                        //Si devuelve 2 es porque no puede tener espacios
                        case 2:

                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*La contraseña no puede tener espacios");
                            break;
                        //Si devuelve 3 es porque debe tener almenos un número.
                        case 3:
                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*Debe contener almenos un número");
                            break;
                        //Si devuelve 4 es porque debe tener una mayúscula almenos
                        case 4:
                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*Debe contener almenos una mayúscula");
                            break;

                    }
                }
                //Pasamos el contenido del editText a String. Aplicamos el metodo para validar password nueva y el return lo guardamos en estadoPassword2
                String passwordRecuperar2 = et_nuevaPassword2.getText().toString();

                Integer estadoPassword2 = restriccionesInicioSesion.validarPasswordNueva(passwordRecuperar2);

                //Si el estadoPassword2 es 0 es correcto, ponemos en errores invisibles
                if(estadoPassword2 == 0){
                    tv_textoErroresPasswordNueva1.setVisibility(View.INVISIBLE);
                    tv_asteriscoErroresPasswordNueva1.setVisibility(View.INVISIBLE);


                }else{
                    switch (estadoPassword2) {
                        //Si devuelve 1 es porque el campo esta vacio.
                        case 1:

                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva2.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*Campo contraseña requerido");
                            break;
                        //Si devuelve 2 es porque no puede tener espacios
                        case 2:
                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva2.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*La contraseña no puede tener espacios");
                            break;
                        //Si devuelve 3 es porque debe tener almenos un número.
                        case 3:
                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva2.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*Debe contener almenos un número");
                            break;
                        //Si devuelve 4 es porque debe tener una mayúscula almenos
                        case 4:
                            tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_asteriscoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordNueva1.setText("*Debe contener almenos una mayúscula");
                            break;

                    }
                }
                //Si las contraseñas estan libre de errores, se comprueban entre ellas.
                if(estadoPassword1 == 0 && estadoPassword2 == 0){
                    //Se comprueba que no sean iguales, si se cumple marcará error y el boolean sera false
                    //Si son iguales se pondra en invisible el error y el bolean sera true.
                    if(!(passwordRecuperar1.equals(passwordRecuperar2))){
                        tv_textoErroresPasswordNueva1.setVisibility(View.VISIBLE);
                        tv_textoErroresPasswordNueva1.setText("*Las contraseñas no coinciden");
                        igualdadPassword = false;
                    }else{
                        tv_textoErroresPasswordNueva1.setVisibility(View.INVISIBLE);
                        igualdadPassword = true;
                    }
                }
                //Si el estado de las passwords son 0 y el boolean es true significará que esta correcto, por tanto, guardamos la contraseña en la bdd y volvemos al login
                if(estadoPassword1 == 0 && estadoPassword2 == 0 && igualdadPassword){
                    String contraseña = passwordRecuperar2;

                    //Funcion BBDD que recoja la contraseña y la actualice.
                    ConexionBDD.actualizarPassword(correo, contraseña);

                    Toast.makeText(getApplicationContext(), "La contraseña se ha actualizado.", Toast.LENGTH_SHORT).show();
                    Intent irLogin = new Intent(GenerarNuevaPassword.this, Login.class);
                    startActivity(irLogin);
                }

            }
        });
    }
}


