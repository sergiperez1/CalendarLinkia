package com.example.proyectofinaldam.InicioSesion;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.proyectofinaldam.R;


public class CodigoRecuperacion extends DialogFragment {
    private EditText et_codigoRecuperacion;
    private Button btn_codigoRecuperacion;
    private TextView tv_codigoRecuperacion;
    private Button btn_cancelarRecuperacion;
    private TextView tv_textoErrorCodigoRecup;

    public CodigoRecuperacion() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.example.proyectofinaldam.R.layout.codigo_recuperar2, container, false);


        btn_codigoRecuperacion = view.findViewById(com.example.proyectofinaldam.R.id.btn_codigoRecuperacion);
        btn_cancelarRecuperacion = view.findViewById(com.example.proyectofinaldam.R.id.btn_cancelar);
        tv_textoErrorCodigoRecup = view.findViewById(com.example.proyectofinaldam.R.id.tv_textoErrorCodigoVerificar);
        et_codigoRecuperacion = view.findViewById(R.id.et_codigo_recuperacion);

        RestriccionesInicioSesion restriccionesInicioSesion = new RestriccionesInicioSesion();

        //Para pasar el codigo de recuperar contraseña al fragment de CodigoRecuperar.
        String codigo = getArguments().getString("codigo");
        //Para recoger el correo que se quiere recuperar.
        String correo = getArguments().getString("correo");

        //Realizamos el OnCLickListener del boton Validar
        btn_codigoRecuperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se transforma el editText y su contenido a un String. Se le aplica en el metodo validarCodigoVerificacion y se guarda su return en una variable integer
                String codigoVerificar = et_codigoRecuperacion.getText().toString();
                String codigoMayus = convertirAMayusculas(codigoVerificar);

                //Si el estadoCodigo es 0 es que esta correcto, por tanto, ponemos los errores en invisibles
                if(codigoMayus.equals(codigo)){

                    tv_textoErrorCodigoRecup.setVisibility(View.INVISIBLE);

                    //Al ser correcto vamos a la pantalla para introducir nueva contraseña
                    Intent irIntroducirPassword = new Intent(getContext(), GenerarNuevaPassword.class);
                    irIntroducirPassword.putExtra("correo" , correo);
                    startActivity(irIntroducirPassword);
                    //Se cierra cuando se verifique el codigo
                    dismiss();
                }else if(codigoMayus.isEmpty()) {

                    //Si devuelve 1 es que el campo esta vacío.

                    tv_textoErrorCodigoRecup.setVisibility(View.VISIBLE);
                    tv_textoErrorCodigoRecup.setText("*Codigo requerido");
                }
                        //Si devuelve 2 es porque es codigo incorrecto al no existir en la bdd
                        else {
                            //SI EL USUARIO NO CORRESPONDE CON EL DE LA BDD
                            tv_textoErrorCodigoRecup.setVisibility(View.VISIBLE);
                            //Codigo incorrecto se mostrará solo en un textview ya que sino se repetirá
                            tv_textoErrorCodigoRecup.setText("*Codigo incorrecto");


                }

            }
        });
        //En el botón de cancelar se cierra la ventana pequeña con el dismiss()
        btn_cancelarRecuperacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }//oncreate

    /**
     * Fncion convertirAMAyusculas. Recoge el texto y lo devuelve en formato Mayus.
     * @param texto
     * @return
     */
    public String convertirAMayusculas(String texto) {
        if (texto != null) {
            return texto.toUpperCase();
        }
        return "";
    }

}