package com.example.proyectofinaldam.InicioSesion;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.enviarCorreo;

import java.util.Random;


public class RecuperarPassword extends AppCompatActivity {
    private TextView tv_tituloRecuperar;
    private EditText et_emailRecuperar;
    private Button btn_enviarRecuperar;
    private TextView tv_volverInicioRecuperar;
    private TextView tv_textoErroresRecPassw;
    private TextView tv_asteriscoRecPassw;

    String TAG = "Codigo";
    String codigo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_password);



        tv_tituloRecuperar = findViewById(R.id.tv_titulo_recuperarpassw);
        et_emailRecuperar = findViewById(R.id.et_email);
        btn_enviarRecuperar = findViewById(R.id.btn_enviar);
        tv_volverInicioRecuperar = findViewById(R.id.tv_volverInicio);
        tv_textoErroresRecPassw = findViewById(R.id.tv_textoErroresRecuperarPassw);
        tv_asteriscoRecPassw = findViewById(R.id.tv_asteriscoRecuperarPassw);

        RestriccionesInicioSesion restriccionesInicioSesion = new RestriccionesInicioSesion();
        ventanaCodigoRecuperar();

        //Si el correo introducido existe en la base de datos al darle a enviar te enviara un codigo de verificacion
        // Se abrira una ventanita para introducir el codigo.
        btn_enviarRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guardamos en un string el contenido del editText. Aplicamos el metodo validar correo y guardamos el return en un integer.
                String correoRecuperar = et_emailRecuperar.getText().toString();
                Integer estadoCorreoRecuperar = restriccionesInicioSesion.validarCorreoRecuperar(correoRecuperar);

                // Si devuelve 0 está correcto.
                if (estadoCorreoRecuperar == 0) {
                    tv_asteriscoRecPassw.setVisibility(View.INVISIBLE);
                    tv_textoErroresRecPassw.setVisibility(View.INVISIBLE);
                    tv_textoErroresRecPassw.setVisibility(View.INVISIBLE);

                    // Si el correo existe en la base de datos se genera un codigo que se pasara al siguiente fragment.
                    codigo = generarRandomCode();
                    Log.d(TAG, "Codigo a poner: " + codigo);
                    CodigoRecuperacion f = new CodigoRecuperacion();
                    Bundle args = new Bundle();
                    args.putString("codigo", codigo);
                    args.putString("correo", correoRecuperar);
                    f.setArguments(args);

                    // Enviar el correo en segundo plano utilizando AsyncTask
                    new SendEmailTask().execute(correoRecuperar, "Recuperacion Contraseña", "El codigo para recuperar la contraseña es: " + codigo);

                    // Se abre una ventana para introducir el codigo al ser correcto el correo.
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    // Mostrar el diálogo
                    f.show(transaction, "CodigoRecuperacion");

                } else {
                    switch (estadoCorreoRecuperar) {
                        // Si devuelve 1 es porque el campo usuario esta vacio
                        case 1:
                            tv_asteriscoRecPassw.setVisibility(View.VISIBLE);
                            tv_textoErroresRecPassw.setVisibility(View.VISIBLE);
                            tv_textoErroresRecPassw.setText("*Campo correo requerido");
                            break;
                        // Si devuelve 2 es porque el correo no corresponde con la bdd
                        case 2:
                            // SI EL CORREO NO CORRESPONDE CON EL DE LA BDD
                            tv_asteriscoRecPassw.setVisibility(View.VISIBLE);
                            tv_textoErroresRecPassw.setVisibility(View.VISIBLE);
                            tv_textoErroresRecPassw.setText("*Correo no registrado");
                            break;
                    }
                }
            }
        });
        //Este botón te devuelve al inicio
        tv_volverInicioRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    //Este metodo genera una ventana pequeña encima de la pantalla recuperar contraseña.
    public void ventanaCodigoRecuperar(){

        // obtener las dimensiones de la pantalla
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // calcular los valores correspondientes al 0% del ancho y alto de la pantalla
        int dialogWidth = (int) (screenWidth * 0.7);
        int dialogHeight = (int) (screenHeight * 0.4);

        // crear un objeto Dialog y establecer el estilo personalizado y las dimensiones
        Dialog dialog = new Dialog(this, R.style.VentanaDialogRecuperar);
        dialog.setContentView(R.layout.codigo_recuperar2);
        Window window = dialog.getWindow();
        window.setLayout(dialogWidth, dialogHeight);

    }
    //Creamos funcion que genera codigo automaticamente.

        public static String generarRandomCode() {
            int codeLength = 5;
            String characters = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
            StringBuilder codeBuilder = new StringBuilder();

            Random random = new Random();
            for (int i = 0; i < codeLength; i++) {
                int randomIndex = random.nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                codeBuilder.append(randomChar);
            }

            return codeBuilder.toString();
        }
    //Clase que envia el Mail en un hilo diferente usando AsyncTask
    private class SendEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String correoRecuperar = params[0];
            String asunto = params[1];
            String mensaje = params[2];

            enviarCorreo eC = new enviarCorreo();
            eC.enviarCorreo(correoRecuperar, asunto, mensaje);
            Log.d("correo", "contenido del correo: " + eC);

            return null;
        }
    }


    }//Fin del archivo

