package com.example.proyectofinaldam.gestion_grupos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.evento.CrearEvento;
import com.example.proyectofinaldam.grupos.AdaptadorSpinnerColores;
import com.example.proyectofinaldam.interfaces;

import java.util.ArrayList;

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
    private ImageButton btn_annadir_miembros;
    private ImageButton btn_borrar_miembros;
    private ListView lv_miembros_annadidos;

    private GradientDrawable bolita;
    private Button btn_guardar_grupo;
    private TextView error_nombre_grupo;
    private TextView error_descripcion_grupo;
    private TextView tv_error_elimina_admin;
    private TextView error_miembros_grupo;

    private ArrayList<Integer> miembros = new ArrayList<>();

    private String Creacion;
    private AdaptadorMiembros adapMiembros;

    int idUsuario;
    int posicionSeleccionada;
    int idUserAEliminar;

    int ID; //grupo nuevo
    private static final int REQUEST_CODE_MIEMBROS = 1;
    ListAdapter parentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_grupo);

        // Obtener el idUsuario pasado desde la actividad anterior
        idUsuario = getIntent().getIntExtra("idUsuario", 0);
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
        btn_annadir_miembros = (ImageButton) findViewById(R.id.btn_annadir_miembros);
        // Boton que nos elimina un miembro del grupo
        btn_borrar_miembros = (ImageButton) findViewById(R.id.btn_borrar_miembros);
        // ListView con la lista de miembros que se han añadido al grupo
        lv_miembros_annadidos = (ListView) findViewById(R.id.lv_miembros_annadidos);
        // Botón para guardar el grupo creado
        btn_guardar_grupo = (Button) findViewById(R.id.btn_guardar_grupo);
        tv_error_elimina_admin = (TextView) findViewById(R.id.tv_error_elimina_admin);


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

        // Añadimos a la lista de ids de usuarios miembros el id del usuario que está creando el grupo
        miembros.add(idUsuario);

        ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
            @Override
            public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                lv_miembros_annadidos.setAdapter(adapMiembros);
                parentAdapter = lv_miembros_annadidos.getAdapter();
                Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
            }
        });


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
                            ID = i;

                            ConexionBDD.crearGrupo(i, nombre, descripcion, color, miembros, idUsuario, Creacion);

                            for (Integer idActualizar : miembros) {
                                ConexionBDD.actualizarUsuario(idActualizar, ID, new interfaces.OnUsuarioActualizadoListener() {
                                    @Override
                                    public void onUsuarioActualizado() {
                                        // Se ejecuta cuando el usuario ha sido actualizado correctamente
                                        Log.d("TAG", "Usuario actualizado correctamente");
                                    }
                                });
                            }
                            Toast.makeText(getApplicationContext(), "Grupo creado!", Toast.LENGTH_SHORT);

                        }
                    });

                    Intent irPantallaPrincipal = new Intent(CrearGrupo.this, PantallaPrincipal.class);
                    irPantallaPrincipal.putExtra("idUsuario", idUsuario);
                    startActivity(irPantallaPrincipal);
                }

            }//Cierra onclick
        });


        // Cuando apretamos un usuario de la lista se selecciona y recoge su id
        lv_miembros_annadidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicionSeleccionada = position;
                view.setSelected(true);

                Object objetoSeleccionado = parentAdapter.getItem(posicionSeleccionada);

                if(objetoSeleccionado instanceof Usuario){
                    Usuario usuario = (Usuario) objetoSeleccionado;
                    idUserAEliminar = usuario.getId();
                    Log.d("TAG", "ID DEL MIEMBRO A ELIMINAR " + idUserAEliminar);
                }
            }
        });


        // Cuando apretamos el boton de eliminar un miembro, comprobamos si el usuario es administrador,
        // Si no lo es podemos borrarlo.
        btn_borrar_miembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Comprobamos que el usuario que queremos borrar no es el administrador.
                // Si es el administrador no se podrá borrar, si no lo es podremos borrarlo
                if(idUserAEliminar == idUsuario){
                    Toast.makeText(getApplicationContext(), "ES EL ADMIN, NO SE PUEDE BORRAR!", Toast.LENGTH_SHORT);
                    tv_error_elimina_admin.setVisibility(View.VISIBLE);

                }else{
                    tv_error_elimina_admin.setVisibility(View.INVISIBLE);
                    miembros.remove(Integer.valueOf(idUserAEliminar));
                    adapMiembros.notifyDataSetChanged();

                    ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                        @Override
                        public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                            adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                            lv_miembros_annadidos.setAdapter(adapMiembros);
                            parentAdapter = lv_miembros_annadidos.getAdapter();
                            Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                        }
                    });

                }


            }
        });

    }

    // Método que utiliza el botón de añadir miembros para llevarnos a la pantalla de añadir miembros al grupo
    public void AnnadirMiembros(View view) {

        Intent intent2 = new Intent(this, AnnadirMiembros.class);
        ArrayList<Integer> miembrosArrayList = new ArrayList<>(miembros);
        intent2.putIntegerArrayListExtra("miembros", miembrosArrayList);
        startActivityForResult(intent2, REQUEST_CODE_MIEMBROS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MIEMBROS && resultCode == RESULT_OK) {
            if (data != null) {
                int idUserAAnnadir = data.getIntExtra("idUserAAnnadir", -1);
                if (idUserAAnnadir != -1) {
                    miembros.add(idUserAAnnadir);

                    for (Integer idActualizar : miembros) {
                        ConexionBDD.actualizarUsuario(idActualizar, ID, new interfaces.OnUsuarioActualizadoListener() {
                            @Override
                            public void onUsuarioActualizado() {
                                Log.d("TAG", "Usuario actualizado correctamente");
                            }
                        });
                    }

                    ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                        @Override
                        public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                            adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                            lv_miembros_annadidos.setAdapter(adapMiembros);
                            parentAdapter = lv_miembros_annadidos.getAdapter();
                            Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                        }
                    });

                    adapMiembros.notifyDataSetChanged();


                }
            }
        }
    }
}
