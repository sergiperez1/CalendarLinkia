package com.example.proyectofinaldam.InicioSesion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectofinaldam.ConexionBDD;

import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.interfaces;


public class Login extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRecupContraseña;
    private TextView tvLogin;
    private TextView tv_registro;
    private TextView tv_asteriscoUsuario;
    private TextView tv_asteriscoPassword;
    private TextView tv_textoErroresUsuario;
    private TextView tv_textoErroresPassword;


    // Validar los datos
    ConexionBDD conexionBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tvLogin = findViewById(R.id.tv_login);
        etUsuario = findViewById(R.id.et_usuario);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRecupContraseña = findViewById(R.id.btn_recuperar_password);
        tv_registro = findViewById(R.id.tv_registro);
        tv_asteriscoUsuario = findViewById(R.id.tv_asteriscoUsuario);
        tv_asteriscoPassword = findViewById(R.id.tv_asteriscoPassword);
        tv_textoErroresUsuario = findViewById(R.id.tv_textoErroresUsuario);
        tv_textoErroresPassword = findViewById(R.id.tv_textoErroresPassword);

        conexionBDD = new ConexionBDD();
        conexionBDD.inicializarFirebase();
        conexionBDD.getFirestore(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Asignamos variable al edittext.
                String nombre = etUsuario.getText().toString();
                String password = etPassword.getText().toString();

                //Comprobamos que el usuario y password es correcto
                int usuarioValido = RestriccionesInicioSesion.validarUsuario(nombre);
                int passwordValida = RestriccionesInicioSesion.validarPassword(password);

                tv_asteriscoUsuario.setVisibility(View.INVISIBLE);
                tv_textoErroresUsuario.setVisibility(View.INVISIBLE);
                tv_textoErroresPassword.setVisibility(View.INVISIBLE);
                tv_asteriscoPassword.setVisibility(View.INVISIBLE);


                if(usuarioValido == 0){
                    tv_asteriscoUsuario.setVisibility(View.INVISIBLE);
                    tv_textoErroresUsuario.setVisibility(View.INVISIBLE);
                }else if(usuarioValido == 1){
                    //campo usuario vacio
                        tv_asteriscoUsuario.setVisibility(View.VISIBLE);
                        tv_textoErroresUsuario.setVisibility(View.VISIBLE);
                        tv_textoErroresUsuario.setText("*Campo usuario requerido");

                    }else if(usuarioValido == 2){
                        //SI EL USUARIO NO CORRESPONDE CON EL DE LA BDD
                        tv_asteriscoUsuario.setVisibility(View.VISIBLE);
                        tv_textoErroresUsuario.setVisibility(View.VISIBLE);
                        //Datos incorrectos se mostrará solo en un textview ya que sino se repetirá
                        //Ademas el usuario no debe saber si tiene mal el usuario o la contraseña por seguridad
                        tv_textoErroresUsuario.setText("*Datos incorrectos");
                    }

                if(passwordValida == 0) {
                    tv_textoErroresPassword.setVisibility(View.INVISIBLE);
                    tv_asteriscoPassword.setVisibility(View.INVISIBLE);

                }else if(passwordValida == 1){
                        tv_asteriscoPassword.setVisibility(View.VISIBLE);
                        tv_textoErroresPassword.setVisibility(View.VISIBLE);
                        tv_textoErroresPassword.setText("*Campo contraseña requerido");

                    }else if(passwordValida == 2){
                        //SI LA CONTRASEÑA NO CORRESPONDE CON EL DE LA BDD
                        tv_asteriscoPassword.setVisibility(View.VISIBLE);
                        tv_textoErroresPassword.setVisibility(View.VISIBLE);
                        //Datos incorrectos se mostrará solo en un textview ya que sino se repetirá.
                        //Ademas el usuario no debe saber si tiene mal el usuario o la contraseña por seguridad
                        tv_textoErroresUsuario.setText("*Datos incorrectos");
                        tv_textoErroresPassword.setVisibility(View.INVISIBLE);

                    }
                if(usuarioValido == 0 && passwordValida == 0){

                    //Funcion que extrae el Id del usuario del nombre con el que inicia.
                    ConexionBDD.getIdUsuario(nombre, new interfaces.OnGetIdUsuarioListener() {
                        @Override
                        public void onGetIdUsuario(Integer idUsuario) {
                            if (idUsuario != null) {
                                // El usuario existe y se obtuvo su Id
                                Log.d(ConexionBDD.TAG,"El usuario existe y su valor de ID es: " + idUsuario);
                                // Guardar el Id en la preferencia compartida o en otro lugar adecuado
                                //Cambia de pantalla.
                                Intent irPantallaPrincipal = new Intent(Login.this, PantallaPrincipal.class);
                                irPantallaPrincipal.putExtra("ID_USUARIO", idUsuario);
                                startActivity(irPantallaPrincipal);
                            } else {
                                Log.d(ConexionBDD.TAG,"El usuario no existe: " );
                            }
                        }
                    });
                }
            }
        });

        //Al darle a recuperar contraseña nos lleva a la ventana donde introducimos el correo electronico.
        btnRecupContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irRecuperarPassw = new Intent(Login.this, RecuperarPassword.class);
                startActivity(irRecuperarPassw);
            }
        });
        //Al darle a registrarnos nos lleva a la ventana donde podremos registrarnos.
        tv_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irRegistro = new Intent(Login.this, Registro.class);
                startActivity(irRegistro);
            }
        });

    } //termina on create.





} //Termina el login.