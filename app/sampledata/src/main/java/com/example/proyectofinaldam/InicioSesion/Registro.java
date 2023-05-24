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
import com.example.proyectofinaldam.interfaces;

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {
    private EditText et_usuarioRegistro;
    private EditText et_passwordRegistro;
    private EditText et_correoRegistro;
    private Button btn_registro;
    private TextView tv_registro;
    private TextView tv_asteriscoCorreoRegistro;
    private TextView tv_asteriscoUsuarioRegistro;
    private TextView tv_asteriscoPasswordRegistro;
    private TextView tv_textoErroresCorreoRegistro;
    private TextView tv_textoErroresUsuarioRegistro;
    private TextView tv_textoErroresPasswordRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        et_usuarioRegistro = findViewById(R.id.et_usuarioRegistro);
        et_passwordRegistro = findViewById(R.id.et_passwordRegistro);
        et_correoRegistro = findViewById(R.id.et_correoRegistro);
        btn_registro = findViewById(R.id.btn_registro);


        tv_asteriscoCorreoRegistro = findViewById(R.id.tv_asteriscoCorreoR);
        tv_asteriscoUsuarioRegistro = findViewById(R.id.tv_asteriscoUsuarioR);
        tv_asteriscoPasswordRegistro = findViewById(R.id.tv_asteriscoPasswordR);
        tv_textoErroresCorreoRegistro  = findViewById(R.id.tv_textoErroresCorreoR);
        tv_textoErroresUsuarioRegistro = findViewById(R.id.tv_textoErroresUsuarioR);
        tv_textoErroresPasswordRegistro = findViewById(R.id.tv_textoErroresPasswordR);





        RestriccionesInicioSesion restriccionesInicioSesion = new RestriccionesInicioSesion();

        //Al darle al boton comprobara el formato de los datos, si ya existe y en el caso que no exista
        // se guardara en la base de datos.
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardamos en un string el contenido del editText. Aplicamos el metodo validar correo y guardamos el return en un integer.
                String correoRegistro = et_correoRegistro.getText().toString().trim();
                Integer estadoCorreoRegistro = restriccionesInicioSesion.validarCorreo(correoRegistro);
                //Si devuelve 0 esta correcto.
                if(estadoCorreoRegistro == 0){
                    tv_textoErroresCorreoRegistro.setVisibility(View.INVISIBLE);
                    tv_asteriscoCorreoRegistro.setVisibility(View.INVISIBLE);

                }else{
                    switch (estadoCorreoRegistro) {
                        //Si devuelve 1 es porque el campo correo esta vacio
                        case 1:
                            tv_asteriscoCorreoRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresCorreoRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresCorreoRegistro.setText("*Campo correo requerido");
                            break;
                        //Si devuelve 2 es porque el formato es incorrecto o tiene caracteres especiales.
                        //Debe tener @, com|es|cat|net|edu|gov|org y no caracteres especiales.
                        //Si el correo formato del correo no es el correcto se veran los errores.
                        case 2:
                            tv_asteriscoCorreoRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresCorreoRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresCorreoRegistro.setText("*Formato incorrecto o caracteres especiales");
                            break;
                    }
                }
                //Guardamos en un string el contenido del editText. Aplicamos el metodo validar usuario y guardamos el return en un integer.
                String usuarioRegistro = et_usuarioRegistro.getText().toString();
                Integer estadoUsuarioRegistro = restriccionesInicioSesion.validarUsuarioRegistro(usuarioRegistro);
                //Si devuelve 0 esta correcto.
                if(estadoUsuarioRegistro == 0){
                    tv_textoErroresUsuarioRegistro.setVisibility(View.INVISIBLE);
                    tv_asteriscoUsuarioRegistro.setVisibility(View.INVISIBLE);



                }else{
                    switch (estadoUsuarioRegistro) {
                        //Si devuelve 1 es porque el campo usuario esta vacio
                        case 1:
                            tv_asteriscoUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setText("*Campo usuario requerido");
                            break;
                        //Si devuelve 2 es porque el dato no corresponde con la bdd o ya existe
                        case 2:
                            //SI EL USUARIO NO CORRESPONDE CON EL DE LA BDD
                            tv_asteriscoUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setVisibility(View.VISIBLE);
                            //Datos incorrectos se mostrará solo en un textview ya que sino se repetirá
                            //Ademas el usuario no debe saber si tiene mal el usuario o la contraseña por seguridad
                            tv_textoErroresUsuarioRegistro.setText("*Datos incorrectos");
                            break;
                        //Si devuelve 3 es porque el usuario tiene espacios
                        case 3:
                            tv_asteriscoUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setText("*El usuario no puede tener espacios");
                            break;
                        //Si devuelve 4 es porque tiene caracteres especiales
                        case 4:
                            tv_asteriscoUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresUsuarioRegistro.setText("*El usuario no puede tener caracteres especiales");
                            break;


                    }
                }
                //Guardamos en un string el contenido del editText. Aplicamos el metodo validar contraseña y guardamos el return en un integer.
                String passwordRegistro = et_passwordRegistro.getText().toString();
                Integer estadoPasswordRegistro = restriccionesInicioSesion.validarPasswordRegistro(passwordRegistro);
                //Si devuelve 0 esta correcto.
                if(estadoPasswordRegistro == 0){
                    tv_textoErroresPasswordRegistro.setVisibility(View.INVISIBLE);
                    tv_asteriscoPasswordRegistro.setVisibility(View.INVISIBLE);

                }else{
                    switch (estadoPasswordRegistro) {
                        //Si devuelve 1 es porque el campo contraseña esta vacio
                        case 1:
                            tv_asteriscoPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setText("*Campo contraseña requerido");
                            break;
                        //Si devuelve 2 es porque el dato no corresponde con la bdd
                        case 2:
                            //SI LA CONTRASEÑA NO CORRESPONDE CON EL DE LA BDD
                            tv_asteriscoUsuarioRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setVisibility(View.VISIBLE);
                            //Datos incorrectos se mostrará solo en un textview ya que sino se repetirá.
                            //Ademas el usuario no debe saber si tiene mal el usuario o la contraseña por seguridad
                            tv_textoErroresUsuarioRegistro.setText("*Datos incorrectos");
                            break;
                        //Si devuelve 3 es porque la password tiene espacios
                        case 3:
                            tv_asteriscoPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setText("*La contraseña no puede tener espacios");
                            break;
                        //Si devuelve 4 es porque tiene caracteres especiales
                        case 4:
                            tv_asteriscoPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setText("*Debe contener almenos un número");
                            break;
                        //Si devuelve 5 es porque debe contener almenos una mayúscula
                        case 5:
                            tv_asteriscoPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setVisibility(View.VISIBLE);
                            tv_textoErroresPasswordRegistro.setText("*Debe contener almenos una mayúscula");
                            break;


                    }

                }

                // Si el correo, usuario y la contraseña son correctos y existen en la bdd, su variable estado sera 0.
                // Por tanto con este if nos aseguramos que los datos sean correctos antes de ir a la registro

                if(estadoUsuarioRegistro == 0 && estadoPasswordRegistro == 0 && estadoCorreoRegistro == 0) {

                    //Se llama a la funcion getIDRegistro para obtener el ultimo ID de usuarios y asignarle +1  al proximo.
                    ConexionBDD.getIDnewUsuario(new interfaces.OnGetNextIdValueListener() {
                        @Override
                        public void onGetNextIdValue(int i) {
                            List<Integer> grupos = new ArrayList<>();
                            grupos.add(0);
                            Log.d(ConexionBDD.TAG,"El siguiente valor de ID es: " + i);
                            ConexionBDD.RegistrarUsuario(i,correoRegistro,usuarioRegistro,passwordRegistro, grupos);
                        }
                    });


                    Toast.makeText(getApplicationContext(), "Se ha registrado con éxito", Toast.LENGTH_SHORT).show();
                    Intent irLogin = new Intent(Registro.this, Login.class);
                    startActivity(irLogin);
                }
            }
        });

    }
}
