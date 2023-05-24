package com.example.proyectofinaldam.evento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CrearEvento extends AppCompatActivity {
    public EditText et_nombreEvento;
    public EditText et_fechaLimiteEncuesta;
    public EditText et_opcion1;
    public EditText et_opcion2;
    public EditText et_opcion3;
    private RadioGroup rdGroupTipoFecha;
    private RadioButton rdBtn_tipoFechaSi;
    private RadioButton rdBtn_tipoFechaNo;
    private Button btn_crear_evento;
    public Spinner sp_grupoEvento;
    public EditText et_descripcionCrearEvento;
    private TextView tv_preguntaTipoFecha;
    private TextView tv_fechaLimiteEncuesta;
    private TextView tv_diasElegirEncuesta;
    private TextView tv_asteriscoNombre;
    private TextView tv_asteriscoGrupo;
    private TextView tv_asteriscoDescripcion;
    private TextView tv_asteriscoFecha;
    private TextView tv_asteriscoOpcion1;
    private TextView tv_asteriscoOpcion2;
    private TextView tv_asteriscoOpcion3;
    public TextView tv_textoErroresNombre;
    public TextView tv_textoErroresFechaEv;
    public TextView tv_textoErroresFechaEnc;
    public TextView tv_textoErroresOpcion;
    public int numOpciones = 0;

    public EditText et_FechaFinalEvento;


    //Strings creados para las restricciones
    public String nombreEventocrear;
    public String textoErroresNombre;
    public String textoErroresFechaEv;
    public String textoErroresNombreEnc;
    public String textoErroresOpcion;

    //Booleans para poder pasar a siguiente pagina

    boolean estadoFechaEvento;
    boolean estadoFechaEncuesta;
    boolean estadoOpcion1;
    boolean estadoOpcion2;
    boolean estadoOpcion3;



    /**
     * Por default.
     * El grupo sera mi calendario y la fecha abierta sera no.
     */

    public CrearEvento(){}

    //Funcion que te retorna la fecha de hoy.
    public String FechaActual() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH) + 1; // Sumar 1 al mes porque enero es 0
        int anno = calendar.get(Calendar.YEAR);
        String fechaHoy = String.format("%02d/%02d/%04d", dia, mes, anno);
        return fechaHoy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_evento);



        //Vinculamos los componentes con los del layout.
        et_nombreEvento = findViewById(R.id.et_nombre_eventoCrearE);

        et_fechaLimiteEncuesta = findViewById(R.id.et_fechaLimiteEncuesta);
        et_FechaFinalEvento = findViewById(R.id.et_fechaFinalEvento);
        et_opcion1 = findViewById(R.id.et_opcion1);
        et_opcion2 = findViewById(R.id.et_opcion2);
        et_opcion3 = findViewById(R.id.et_opcion3);
        rdGroupTipoFecha = findViewById(R.id.rdGroup_tipoFecha);
        rdBtn_tipoFechaSi = findViewById(R.id.rdBtn_tipoFechaSi);
        rdBtn_tipoFechaNo = findViewById(R.id.rdBtn_tipoFechaNo);
        btn_crear_evento = findViewById(R.id.btn_crear_evento);
        sp_grupoEvento = findViewById(R.id.sp_grupoCrearE);
        et_descripcionCrearEvento   = findViewById(R.id.et_descripcionCrearE);
        tv_fechaLimiteEncuesta = findViewById(R.id.tv_fechaLimiteEncuesta);
        tv_diasElegirEncuesta = findViewById(R.id.tv_DiasElegir);
        tv_asteriscoNombre = findViewById(R.id.tv_nombreRestriccion);
        tv_asteriscoGrupo = findViewById(R.id.tv_grupoRestriccion);
        tv_asteriscoDescripcion = findViewById(R.id.tv_descRestriccion);
        tv_asteriscoFecha = findViewById(R.id.tv_fechaRestriccion);
        tv_asteriscoOpcion1 = findViewById(R.id.tv_opcion1Restriccion);
        tv_asteriscoOpcion2 = findViewById(R.id.tv_opcion2Restriccion);
        tv_asteriscoOpcion3 = findViewById(R.id.tv_opcion3Restriccion);
        tv_textoErroresNombre = findViewById(R.id.tv_textoErroresNombre);
        tv_textoErroresFechaEv = findViewById(R.id.tv_textoErroresFechaEv);
        tv_textoErroresFechaEnc = findViewById(R.id.tv_textoErroresFechaEnc);
        tv_textoErroresOpcion = findViewById(R.id.tv_textoErroresOpciones);





        textoErroresNombre = tv_textoErroresNombre.getText().toString();
        textoErroresFechaEv = tv_textoErroresFechaEv.getText().toString();
        textoErroresNombreEnc = tv_textoErroresFechaEnc.getText().toString();



        RestriccionesEvento restriccionesEvento = new RestriccionesEvento();




        //Creamos un array adapter y introducimos en el spinner datos de ejemplo.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Opción 1", "Opción 2", "Opción 3"});
        sp_grupoEvento.setAdapter(adapter);







        rdBtn_tipoFechaSi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mostrarCampos();
                    //Al darle al SI  el editText para la fecha del evento pasa a estar vacio.
                    et_fechaLimiteEncuesta.setText("");
                    //Por tanto, hacemos limpieza de los errores visuales relacionados con las fechas.
                    tv_asteriscoFecha.setVisibility(View.INVISIBLE);
                    tv_textoErroresFechaEnc.setVisibility(View.INVISIBLE);
                    tv_textoErroresFechaEv.setVisibility(View.INVISIBLE);
                }
            }
        });
        rdBtn_tipoFechaNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ocultarCampos();
                    //Al  darle al NO el editText para la fecha del evento pasa a estar vacio.
                    //También las opciones
                    et_FechaFinalEvento.setText("");
                    et_opcion1.setText("");
                    et_opcion2.setText("");
                    et_opcion3.setText("");
                    numOpciones = 3;
                    //Por tanto, hacemos limpieza de los errores visuales relacionados con las fechas.
                    tv_asteriscoFecha.setVisibility(View.INVISIBLE);
                    tv_textoErroresFechaEnc.setVisibility(View.INVISIBLE);
                    tv_textoErroresFechaEv.setVisibility(View.INVISIBLE);
                    tv_textoErroresOpcion.setVisibility(View.INVISIBLE);
                }
            }
        });




        //Realizamos un onclickListener para que al pulsar haga... boton Crear
        btn_crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Restriccion de NOMBRE:
                //Pasamos el editText a String, luego utilizamos el metodo de restriccion para nombre crear evento y el valor que devuelve lo ponemos en el Integer.
                nombreEventocrear = et_nombreEvento.getText().toString();
                Integer estadoErrorNombre = restriccionesEvento.restriccionNombreCrearEv(nombreEventocrear);
                //Si estadoErrorNombre da 0 es que esta correcto, por tanto, textView en invisibles.
                if (estadoErrorNombre == 0) {
                    tv_asteriscoNombre.setVisibility(View.INVISIBLE);
                    tv_textoErroresNombre.setVisibility(View.INVISIBLE);

                    //COMPROBARÁ QUE LO INTRODUCIDO ES CORRECTO Y GUARDARA EN BASE DE DATOS


                } else {
                    switch (estadoErrorNombre) {
                        //Si nos devuelve un 1 sera porque esta vacio
                        case 1:

                            tv_asteriscoNombre.setVisibility(View.VISIBLE);
                            tv_textoErroresNombre.setVisibility(View.VISIBLE);
                            tv_textoErroresNombre.setText("*Campo nombre requerido");
                            break;
                        //Si nos devuelve un 2 es porque tiene numeros o caracteres especiales
                        case 2:

                            tv_asteriscoNombre.setVisibility(View.VISIBLE);
                            tv_textoErroresNombre.setVisibility(View.VISIBLE);
                            tv_textoErroresNombre.setText("*No pueden haber números ni caracteres especiales");
                            break;

                    }
                }

                // Creamos variables dia, mes, año para poder calcular la fecha actual.

                    Calendar calendar = Calendar.getInstance();
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int mes = calendar.get(Calendar.MONTH) + 1; // Sumar 1 al mes porque enero es 0
                    int anno = calendar.get(Calendar.YEAR);
                    String fechaHoy = String.format("%02d/%02d/%04d", dia, mes, anno);



                // Verificar cuál EditText tiene contenido y hacer las validaciones correspondientes
                //Realizamos un if y else if para que salga error de campo requerido cuando el campo vacio sea el que ve el usuario.
                //Si el campo Fecha encuesta esta vacio y el campo Fecha evento no, se cumplira el IF. Poniendo los textView en visibles
                //y el estado de fechaEvento sera false.
                if ( !(et_FechaFinalEvento.getText().toString().isEmpty()) && et_fechaLimiteEncuesta.getText().toString().isEmpty()) {
                    tv_asteriscoFecha.setVisibility(View.VISIBLE);
                    tv_textoErroresFechaEv.setVisibility(View.VISIBLE);
                    tv_textoErroresFechaEv.setText("*Campo fecha requerido");
                    estadoFechaEvento = false;
                    //Si el campo Fecha final evento esta vacio y fecha encuesta no lo esta se cumplira el if.Poniendo los textView en visibles
                    // y el estado de fechaEncuesta sera false.
                }else if (!(et_fechaLimiteEncuesta.getText().toString().isEmpty())&& et_FechaFinalEvento.getText().toString().isEmpty()) {
                    tv_asteriscoFecha.setVisibility(View.VISIBLE);
                    tv_textoErroresFechaEnc.setVisibility(View.VISIBLE);
                    tv_textoErroresFechaEnc.setText("*Campo fecha requerido");
                    estadoFechaEncuesta = false;
                    //Si los dos estan vacios, saldra solo un textView con el error y los dos estados seran false.
                }else if((et_FechaFinalEvento.getText().toString().isEmpty()) && (et_fechaLimiteEncuesta.getText().toString().isEmpty())) {
                    tv_asteriscoFecha.setVisibility(View.VISIBLE);
                    tv_textoErroresFechaEnc.setVisibility(View.VISIBLE);
                    tv_textoErroresFechaEnc.setText("*Campo fecha requerido");
                    estadoFechaEvento = false;
                    estadoFechaEncuesta = false;
                    tv_textoErroresOpcion.setVisibility(View.INVISIBLE);

                }

                //Restriccion fecha evento
                //Creamos un SimpleFormat dd/mm/yyyy. Si fecha evento no esta vacia se comprobará que la fecha evento es anterior al dia de hoy, si es asi
                //saldran los errores y el estadoFechaEvento sera false. Sino sera true y no saldran los errores
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                if (!et_FechaFinalEvento.getText().toString().isEmpty()) {
                    String fechaEventoStr = et_FechaFinalEvento.getText().toString();
                    dateFormat.setLenient(true); // Hace que la validación sea estricta
                    try {
                        Date fechaEvento = dateFormat.parse(fechaEventoStr);
                        if (fechaEvento.before(dateFormat.parse(fechaHoy))) {
                            tv_asteriscoFecha.setVisibility(View.VISIBLE);
                            tv_textoErroresFechaEv.setVisibility(View.VISIBLE);
                            tv_textoErroresFechaEv.setText("*La fecha del evento no puede ser en el pasado.");
                            estadoFechaEvento = false;

                        } else {
                            tv_asteriscoFecha.setVisibility(View.INVISIBLE);
                            tv_textoErroresFechaEv.setVisibility(View.INVISIBLE);
                            estadoFechaEvento = true;

                            tv_textoErroresFechaEv.setText(null); // Limpiar el TextView de error si la fecha es válida
                        }
                        //Si no es posible realizar el parse a Date es porque el formato es incorrecto, por tanto, marcamos errores y el estado sera false.
                    } catch (ParseException e) {
                        tv_asteriscoFecha.setVisibility(View.VISIBLE);
                        tv_textoErroresFechaEv.setVisibility(View.VISIBLE);
                        tv_textoErroresFechaEv.setText("*Formato de fecha inválido. Debe ser dd/MM/yyyy.");
                        estadoFechaEvento = false;
                    }

                }
                //Restriccion fecha encuesta
                //Si fecha encuesta no esta vacia se comprobará que la fecha encuesta es anterior al dia de hoy, si es asi
                //saldran los errores y el estadoFechaEncuesta sera false. Sino sera true y no saldran los errores
                String fechaLimiteStr = et_fechaLimiteEncuesta.getText().toString();
                if (!et_fechaLimiteEncuesta.getText().toString().isEmpty()) {

                    dateFormat.setLenient(false); // Hace que la validación sea estricta
                    try {
                        Date fechaLimiteEnc = dateFormat.parse(fechaLimiteStr);
                        if (fechaLimiteEnc.before(dateFormat.parse(fechaHoy))) {
                            tv_asteriscoFecha.setVisibility(View.VISIBLE);
                            tv_textoErroresFechaEnc.setVisibility(View.VISIBLE);
                            tv_textoErroresFechaEnc.setText("*La fecha encuesta no puede ser en el pasado.");
                            estadoFechaEncuesta = false;

                        } else {
                            tv_asteriscoFecha.setVisibility(View.INVISIBLE);
                            tv_textoErroresFechaEnc.setVisibility(View.INVISIBLE);

                            tv_textoErroresFechaEnc.setText(null); // Limpiar el TextView de error si la fecha es válida
                            estadoFechaEncuesta = true;
                        }
                        //Si no es posible realizar el parse a Date es porque el formato es incorrecto, por tanto, marcamos errores y el estado sera false.
                    } catch (ParseException e) {
                        tv_asteriscoFecha.setVisibility(View.VISIBLE);
                        tv_textoErroresFechaEnc.setVisibility(View.VISIBLE);
                        tv_textoErroresFechaEnc.setText("*Formato de fecha inválido. Debe ser dd/MM/yyyy.");
                        estadoFechaEncuesta = false;
                    }
                }
                //Restricciones para las opciones.


                //OPCION1
                //Si opcion1 no esta vacia se comprobará que la opcion1 es anterior al dia de hoy, si es asi
                //saldran los errores y el estadoOpcion1 sera false. Sino sera true y no saldran los errores
                if (!et_opcion1.getText().toString().isEmpty()) {
                    String fechaOpcion1Str = et_opcion1.getText().toString();
                    dateFormat.setLenient(false); // Hace que la validación sea estricta
                    try {
                        Date fechaLimiteOp1 = dateFormat.parse(fechaOpcion1Str);
                        if (fechaLimiteOp1.before(dateFormat.parse(fechaHoy))) {
                            tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                            tv_asteriscoOpcion1.setVisibility(View.VISIBLE);
                            tv_textoErroresOpcion.setText("*La fecha opcion 1 no puede ser en el pasado.");
                            estadoOpcion1 = false;
                        } else {
                            tv_asteriscoOpcion1.setVisibility(View.INVISIBLE);
                            tv_textoErroresOpcion.setVisibility(View.INVISIBLE);
                            tv_textoErroresOpcion.setText(null); // Limpiar el TextView de error si la fecha es válida
                            estadoOpcion1 = true;
                        }
                        //Si no es posible realizar el parse a Date es porque el formato es incorrecto, por tanto, marcamos errores y el estado sera false.
                    } catch (ParseException e) {
                        tv_asteriscoOpcion1.setVisibility(View.VISIBLE);
                        tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                        tv_textoErroresOpcion.setText("*Formato de fecha inválido. Debe ser dd/MM/yyyy.");
                        estadoOpcion1 = false;
                    }
                }
                //OPCION 2
                //Si opcion2 no esta vacia se comprobará que la opcion2 es anterior al dia de hoy, si es asi
                //saldran los errores y el estadoOpcion2 sera false. Sino sera true y no saldran los errores
                if (!et_opcion2.getText().toString().isEmpty()) {
                    String fechaOpcion2Str = et_opcion2.getText().toString();
                    dateFormat.setLenient(false); // Hace que la validación sea estricta
                    try {
                        Date fechaLimiteOp2 = dateFormat.parse(fechaOpcion2Str);
                        if (fechaLimiteOp2.before(dateFormat.parse(fechaHoy))) {
                            tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                            tv_asteriscoOpcion2.setVisibility(View.VISIBLE);
                            tv_textoErroresOpcion.setText("*La fecha opcion 2 no puede ser en el pasado.");
                            estadoOpcion2 = false;

                        } else {
                            tv_asteriscoOpcion2.setVisibility(View.INVISIBLE);
                            tv_textoErroresOpcion.setVisibility(View.INVISIBLE);
                            tv_textoErroresOpcion.setText(null); // Limpiar el TextView de error si la fecha es válida
                            estadoOpcion2 = true;
                        }
                        //Si no es posible realizar el parse a Date es porque el formato es incorrecto, por tanto, marcamos errores y el estado sera false.
                    } catch (ParseException e) {
                        tv_asteriscoOpcion2.setVisibility(View.VISIBLE);
                        tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                        tv_textoErroresOpcion.setText("*Formato de fecha inválido. Debe ser dd/MM/yyyy.");
                        estadoOpcion2 = false;
                    }
                }
                //OPCION 3
                //Si opcion3 no esta vacia se comprobará que la opcion3 es anterior al dia de hoy, si es asi
                //saldran los errores y el estadoOpcion3 sera false. Sino sera true y no saldran los errores
                if (!et_opcion3.getText().toString().isEmpty()) {
                    String fechaOpcion3Str = et_opcion3.getText().toString();
                    dateFormat.setLenient(false); // Hace que la validación sea estricta
                    try {
                        Date fechaLimiteOp3 = dateFormat.parse(fechaOpcion3Str);
                        if (fechaLimiteOp3.before(dateFormat.parse(fechaHoy))) {
                            tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                            tv_asteriscoOpcion3.setVisibility(View.VISIBLE);
                            tv_textoErroresOpcion.setText("*La fecha opcion 3 no puede ser en el pasado.");
                            estadoOpcion3 = false;

                        } else {
                            tv_asteriscoOpcion3.setVisibility(View.INVISIBLE);
                            tv_textoErroresOpcion.setVisibility(View.INVISIBLE);
                            tv_textoErroresOpcion.setText(null); // Limpiar el TextView de error si la fecha es válida
                            estadoOpcion3 = true;
                        }
                        //Si no es posible realizar el parse a Date es porque el formato es incorrecto, por tanto, marcamos errores y el estado sera false.
                    } catch (ParseException e) {
                        tv_asteriscoOpcion3.setVisibility(View.VISIBLE);
                        tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                        tv_textoErroresOpcion.setText("*Formato de fecha inválido. Debe ser dd/MM/yyyy.");
                        estadoOpcion3 = false;
                    }
                }


                //Restriccion de que seleccione almenos dos opciones.
                //Hacemos una variable que registre si el editText esta vacio o no. Si no esta vacio se le suma 1, al final si tiene menos de dos marcamos el error.
                //Si tiene menos de 2 ponemos su estado en false. Si tiene mas en true.

                if (!et_opcion1.getText().toString().isEmpty()) {
                    numOpciones++;
                }
                if (!et_opcion2.getText().toString().isEmpty()) {
                    numOpciones++;
                }
                if (!et_opcion3.getText().toString().isEmpty()) {
                    numOpciones++;
                }
                if (numOpciones < 2) {
                    // Si el usuario introdujo menos de dos opciones, mostrar un error
                    tv_textoErroresOpcion.setVisibility(View.VISIBLE);
                    tv_textoErroresOpcion.setText("*Debe introducir al menos dos opciones.");
                    estadoOpcion1 = false;
                    estadoOpcion2 = false;
                    estadoOpcion3 = false;
                } else {
                    // Si el usuario introdujo al menos dos opciones, ocultar el mensaje de error
                    tv_textoErroresOpcion.setVisibility(View.INVISIBLE);
                    estadoOpcion1 = true;
                    estadoOpcion2 = true;
                    estadoOpcion3 = true;
                }
                //Boton para solucionar que al ser predeterminado el boton NO, no se aplica del bien.
                if(rdBtn_tipoFechaNo.isChecked()){
                    tv_textoErroresOpcion.setVisibility(View.INVISIBLE);
                    tv_fechaLimiteEncuesta.setText("Fecha evento:");
                }

                //Si:
                // -> estadoErrorNombre = 0
                // -> estadoOpcion1 = true
                // -> estadoOpcion2= true
                // -> estadoOpcion3= true
                // -> estadoFechaEvento = true o estadoFechaEncuesta = true
                // Saldra un toast indicando que el evento se ha creado correctamente, se creará en la bdd y pasaremos a la pantalla principal.

                if(estadoErrorNombre == 0 && estadoOpcion1 == true && estadoOpcion2 == true && estadoOpcion3 == true && (estadoFechaEvento == true || estadoFechaEncuesta == true)){

                    //BASE DE DATOS CREAR EVENTO

                    Toast.makeText(getApplicationContext(), "Se ha creado el evento correctamente", Toast.LENGTH_SHORT).show();
                    Intent irPantallaPrincipal = new Intent(CrearEvento.this, PantallaPrincipal.class);
                    startActivity(irPantallaPrincipal);
                }



            }
        });

    }







    public void mostrarCampos(){


        tv_fechaLimiteEncuesta.setText("Fecha Encuesta:");
        et_fechaLimiteEncuesta.setVisibility(View.VISIBLE);
        tv_diasElegirEncuesta.setVisibility(View.VISIBLE);
        et_opcion1.setVisibility(View.VISIBLE);
        et_opcion2.setVisibility(View.VISIBLE);
        et_opcion3.setVisibility(View.VISIBLE);
        et_FechaFinalEvento.setVisibility(View.INVISIBLE);


    }
    public void ocultarCampos(){


        tv_fechaLimiteEncuesta.setText("Fecha:");
        et_fechaLimiteEncuesta.setVisibility(View.INVISIBLE);
        tv_diasElegirEncuesta.setVisibility(View.INVISIBLE);
        et_opcion1.setVisibility(View.INVISIBLE);
        et_opcion2.setVisibility(View.INVISIBLE);
        et_opcion3.setVisibility(View.INVISIBLE);
        et_FechaFinalEvento.setVisibility(View.VISIBLE);

    }
/*Crear Evento:

Funcion 1.
Funcion que muestre todos los grupos que esta el usuario que ha iniciado sesion.(laia lo ha hecho)
Pero ponerlo en un spinner.

Funcion 2:
Compruebe el ultimo Id y le sume 1.

Funcion 3:
Recoger todos los datos de Crear evento. (Funcion 2, Nombre, Descripcion,tipo 0 , Date(tipo1 , null), Confirmacion, fk_IdGrupo.

Funcion 4:
Si tipo = 1
Enviar a la ventana crear encuesta con intent. (Fecha Limite, opcion 1,2,3) */


}










