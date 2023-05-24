package com.example.proyectofinaldam.gestion_grupos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.evento.CrearEvento;
import com.example.proyectofinaldam.grupos.AdaptadorSpinnerColores;
import com.example.proyectofinaldam.interfaces;

import java.util.ArrayList;
import java.util.List;

public class CrearGrupo extends AppCompatActivity {

    //Funcion que debe ir dentro de crear.
    //que recoja nombre, descripcion,
    //el color que ha seleccionado el usuario(enlazar color spinner con el recogido).
    //Añadir miembro y lo recoja en el boton crear.
    //Poner como administrador el id del creador del grupo.
    //Al crear haga un select del ultimo grupo y el ID de este grupo sea  +1.

    //Funcion que haga select del nombre de la tabla usuarios y
    //permita hacer un update a la tabla grupos y el campo miembros sea un array de numeros enlazado con nombre.
    private EditText et_nombre_grupo;
    private EditText et_descripcion_grupo;
    private Spinner spn_colores_grupo;
    private Button btn_annadir_miembros;
    private ListView lv_miembros_annadidos;

    private GradientDrawable bolita;
    private Button btn_guardar_grupo;
    private TextView error_nombre_grupo;
    private TextView error_descripcion_grupo;
    private TextView error_text_grupos;
    private TextView error_miembros_grupo;

    private List<Integer> miembros = new ArrayList<>();

    private String Creacion;
    private AdaptadorMiembros adapMiembros;

    int idUsuario;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_grupo);

        // Obtener el idUsuario pasado desde la actividad anterior
        idUsuario = getIntent().getIntExtra("ID_USUARIO", 0);
        Log.d(ConexionBDD.TAG,"El usuario existe: " + idUsuario );
        // 0 es el valor por defecto en caso de que no se encuentre el valor "ID_USUARIO" en el intent


        // Identificamos todos los elementos del layout que nos serán necesarios:

        // Campo para introducir el nombre del grupo
        et_nombre_grupo = (EditText) findViewById(R.id.et_nombre_grupo);
        // Campo para introducir la descripción del grupo
        et_descripcion_grupo = (EditText) findViewById(R.id.et_descripcion_grupo);
        // Spinner de los colores del grupo
        spn_colores_grupo = (Spinner) findViewById(R.id.spn_colores_grupo);
        // Botón que nos lleva a la pestaña de añadir miembros al grupo
        btn_annadir_miembros = (Button) findViewById(R.id.btn_annadir_miembros);
        // ListView con la lista de miembros que se han añadido al grupo
        lv_miembros_annadidos = (ListView) findViewById(R.id.lv_miembros_annadidos);
        // Botón para guardar el grupo creado
        btn_guardar_grupo = (Button) findViewById(R.id.btn_guardar_grupo);
        error_text_grupos = (TextView) findViewById(R.id.error_text_grupos);


        // Lista de los elementos del spinner de colores
        String[] elementos = {"Rojo", "Verde", "Azul", "Cian", "Morado", "Amarillo"};
        // Lista de colores del spinner de colores para pintar cada opción de un color
        int[] colores = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW};

        // Adaptador del spinner de colores
        AdaptadorSpinnerColores adapter = new AdaptadorSpinnerColores(this, android.R.layout.simple_spinner_item, elementos, colores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_colores_grupo.setAdapter(adapter);

        //Le asignamos el dia de hoy.
        CrearEvento evento = new CrearEvento();
        Creacion = evento.FechaActual();


        /*ConexionBDD.obtenerGrupoPorNombre(nom, new VerGrupo.OnGrupoObtenidoListener() {

            @Override
            public void onGrupoObtenido(grupo grupo) {

                Log.d(TAG, "Llega hasta aqui? " + grupo);
                Log.d(TAG, "Pasa bien el nombre del grupo? " + nom);

                List<Integer> miembros = grupo.getMiembros();
                miembros.add(idUsuario);

                ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                    @Override
                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                        adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                        lv_miembros_annadidos.setAdapter(adapMiembros);
                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                    }
                });
            }

        });*/

        //Onclick del boton guardar.
        btn_guardar_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se pasa el edittext a string
                String nombre = et_nombre_grupo.getText().toString();
                String descripcion = et_descripcion_grupo.getText().toString();

                int colorSeleccionado = spn_colores_grupo.getSelectedItemPosition();
                String color = String.valueOf(spn_colores_grupo.getItemAtPosition(colorSeleccionado));

                if (nombre.isEmpty()) {
                    et_nombre_grupo.setError("Llena todos los campos");
                    et_nombre_grupo.setFocusable(true);
                }
                if (descripcion.isEmpty()) {
                    et_descripcion_grupo.setError("Llena todos los campos");
                    et_descripcion_grupo.setFocusable(true);
                } else {
                    ConexionBDD.getIdnewGrupo(new interfaces.OnGetNextIdValueListener() {
                        @Override
                        public void onGetNextIdValue(int i) {
                            List<Integer> miembros = new ArrayList<>();
                            miembros.add(idUsuario);
                            ConexionBDD.crearGrupo(i, nombre, descripcion, color, miembros, idUsuario, Creacion);
                        }
                    });
                    Intent irPantallaPrincipal = new Intent(CrearGrupo.this, PantallaPrincipal.class);
                    startActivity(irPantallaPrincipal);
                }

            }//Cierra onclick
        });


    }

    // Método que utiliza el botón de añadir miembros para llevarnos a la pantalla de añadir miembros al grupo
    public void AnnadirMiembros(View view) {

        Intent intent2 = new Intent(this, AnnadirMiembros.class);
        startActivity(intent2);


    }

    // Función que utiliza el botón crear grupo para guardar el grupo que hemos creado
    @SuppressLint("NotConstructor")
    public void CrearGrupo(View view) {

        // Recogemos en variables los valores recogidos de los campos nombre del grupo y descripción
        String nombre = et_nombre_grupo.getText().toString();
        String descripcion = et_descripcion_grupo.getText().toString();

        // Restricciones:
        // Si el campo nombre está vacío, nos muestra un error de que tenemos que llenar ese campo,
        // y lo mismo para el campo descripción
        if (nombre.equals("")) {
            et_nombre_grupo.setError("Llena todos los campos");
            et_nombre_grupo.setFocusable(true);
        }
        if (descripcion.equals("")) {
            et_descripcion_grupo.setError("Llena todos los campos");
            et_descripcion_grupo.setFocusable(true);
        } else {
            Toast.makeText(this, "Estan correctos!", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 09/05
     * Queda crear una funcion que si hay errores o reestricciones activas no pueda crear el grupo.
     * lo de añadir miembros al grupo cuando se le clicke.
     * Buscador por nombre y boton.
     */

}
