package com.example.proyectofinaldam.gestion_grupos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.proyectofinaldam.Clases.Encuesta;
import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.grupos.AdaptadorSpinnerColores;
import com.example.proyectofinaldam.interfaces;

import java.util.ArrayList;
import java.util.List;

public class ModificarGrupo extends AppCompatActivity {
    public static final String TAG = "ModificarGrupo";

    //Funcion que recoja todos los datos  que ha escrito el usuario y si le da a guardar haga un update del grupo.
    //Funcion que si le das a borrar elimine el grupo con el que has entrado y vuelva a la pantalla anterior.

    //Funcion de miembros
    //1- Hay un listview con todos los miembros.(select x id que muestre nombre).
    // funcion que seleccione el miembro y al darle al boton haga delete.
    //2- La funcion de buscar. Haga un select y los datos(nombre) de ese usuario aparezcan en un listview.
    //3- Al clicar en el nombre se añada al grupo el miembro(nombre).
    //4- El boton añadir sera para volver a la pantalla anterior.

    private EditText et_modif_nombre_grupo;
    private EditText et_modif_descripcion_grupo;
    private Spinner spn_modif_colores_grupo;
    private TextView tv_error_eliminar_admin;
    private ImageButton btn_modif_eliminar_miembro;
    private ImageButton btn_modif_annadir_miembros;
    private Button btn_guardar_modif_grupo;
    private ListView lv_modif_miembros_annadidos;
    private Button btn_modif_borrar_grupo;
    private AdaptadorMiembros adapMiembros;
    int idUsuario;
    int idGrupo;
    ArrayList<Integer> idEventos = new ArrayList<>();
    ArrayList<Integer> idEncuestas = new ArrayList<>();
    int idUserAEliminar;
    int posicionSeleccionada;
    Context context;
    List<Integer> miembros;
    ListAdapter parentAdapter;
    private static final int REQUEST_CODE_MIEMBROS = 1;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.modificar_grupo);

        Intent intent = getIntent();
        idUsuario = getIntent().getIntExtra("idUsuario", idUsuario);
        idGrupo = getIntent().getIntExtra("idGrupo", idGrupo);
        String nomGrupo = intent.getStringExtra("nomGrupo");

        Log.d("TAG", "RECIBIMOS NOMGRUPO A MODIFICAR " + nomGrupo);

        context = getApplicationContext();

        // Campo para modificar el nombre del grupo
        et_modif_nombre_grupo = (EditText) findViewById(R.id.et_modif_nombre_grupo);
        // Campo para modificar la descripción del grupo
        et_modif_descripcion_grupo = (EditText) findViewById(R.id.et_modif_descripcion_grupo);
        // Spinner de los colores del grupo
        spn_modif_colores_grupo = (Spinner) findViewById(R.id.spn_modif_color);
        // Boton eliminar miembro
        btn_modif_eliminar_miembro = (ImageButton) findViewById(R.id.btn_modif_eliminar_miembro);
        // Boton añadir miembro que te lleva a la pantallita de añadir
        btn_modif_annadir_miembros = (ImageButton) findViewById(R.id.btn_modif_annadir_miembros);
        // Listview que muestra los miembros del grupo
        lv_modif_miembros_annadidos = (ListView) findViewById(R.id.lv_modif_miembros_annadidos);
        lv_modif_miembros_annadidos.setClickable(true);
        // Boton borrar grupo
        btn_modif_borrar_grupo = (Button) findViewById(R.id.btn_modif_borrar_grupo);
        btn_guardar_modif_grupo = (Button) findViewById(R.id.btn_guardar_modif_grupo);
        tv_error_eliminar_admin = (TextView) findViewById(R.id.tv_error_eliminar_admin);


        // Lista de los elementos del spinner de colores
        String[] elementos = {"Rojo", "Verde", "Azul", "Cian", "Morado", "Amarillo"};
        // Lista de colores del spinner de colores para pintar cada opción de un color
        int[] colores = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW};

        // Adaptador del spinner de colores
        AdaptadorSpinnerColores adapter = new AdaptadorSpinnerColores(this,
                android.R.layout.simple_spinner_item, elementos, colores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_modif_colores_grupo.setAdapter(adapter);

        // Obtenemos el grupo por id y rellenamos los campos con los datos del grupo seleccionado
        ConexionBDD.obtenerGrupoPorId(idGrupo, new VerGrupo.OnGrupoObtenidoListener() {

            @Override
            public void onGrupoObtenido(Grupo grupo) {

                et_modif_nombre_grupo.setText(grupo.getNombre());
                et_modif_descripcion_grupo.setText(grupo.getDescripcion());

                miembros = grupo.getMiembros();

                int indiceColor = getIndiceColor(grupo);

                spn_modif_colores_grupo.setSelection(indiceColor);

                ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                    @Override
                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                        adapMiembros = new AdaptadorMiembros(usuarios, context);
                        lv_modif_miembros_annadidos.setAdapter(adapMiembros);
                        parentAdapter = lv_modif_miembros_annadidos.getAdapter();
                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                    }
                });
            }
        });
        //Para obtener el idevento y idEncuesta para eliminar un grupo y sus relaciones
        ConexionBDD.obtenerEventosPorIdGrupo(idGrupo, new interfaces.OnEventoObtenidoListener() {
            @Override
            public void onEventoObtenido(Evento evento) {
                idEventos.add(evento.getId());
                for(int idEvento : idEventos){
                // Obtener las encuestas de cada evento
                ConexionBDD.obtenerEncuestasPorId(idEvento, new interfaces.onEncuestaObtenidaListener() {
                    @Override
                    public void onEncuestaObtenida(Encuesta encuesta) {
                        idEncuestas.add(encuesta.getId());
                    }
                });

                }

                Log.d(TAG, "Arraylist de eventos? " + idEventos);
                Log.d(TAG, "Arraylist de idEncuestas? " + idEncuestas);
            }
        });

        posicionSeleccionada = -1;

        idUserAEliminar = -1;

        // Listview de los miembros del grupo
        lv_modif_miembros_annadidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        // Cuando apretamos el boton elimina un miembro del grupo que hayamos seleccionado.
        // Comprobamos si es el administrador, si lo es no dejamos borrar, pero si no lo es, lo borramos
        btn_modif_eliminar_miembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(idUserAEliminar == -1){
                    Toast.makeText(context, "Selecciona un usuario primero", Toast.LENGTH_SHORT).show();
                }else {
                    // Comprobamos que el usuario que queremos borrar no es el administrador.
                    // Si es el administrador no se podrá borrar, si no lo es podremos borrarlo
                    ConexionBDD.esAdminGrupo(idUserAEliminar, idGrupo, new interfaces.OnCheckFieldListener() {
                        @Override
                        public void onFieldExists(boolean exists) {
                            if (exists) {
                                Toast.makeText(context, "ES EL ADMIN, NO SE PUEDE BORRAR!", Toast.LENGTH_SHORT).show();
                                tv_error_eliminar_admin.setVisibility(View.VISIBLE);

                            }else if(!exists){
                                tv_error_eliminar_admin.setVisibility(View.INVISIBLE);
                                miembros.remove(Integer.valueOf(idUserAEliminar));
                                adapMiembros.notifyDataSetChanged();

                                ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                                    @Override
                                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                                        adapMiembros = new AdaptadorMiembros(usuarios, context);
                                        lv_modif_miembros_annadidos.setAdapter(adapMiembros);
                                        parentAdapter = lv_modif_miembros_annadidos.getAdapter();
                                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                                    }
                                });

                                for (Integer idActualizar : miembros) {
                                    ConexionBDD.actualizarUsuarioElimGrupo(idActualizar, idGrupo, new interfaces.OnUsuarioActualizadoListener() {
                                        @Override
                                        public void onUsuarioActualizado() {
                                            Log.d("TAG", "Usuario actualizado correctamente");
                                        }
                                    });
                                }

                            }
                        }
                    });

                }

            }
        });

        // Este botón borra el grupo de la BD y actualiza los usuarios para que se borre el grupo en sus grupos
        btn_modif_borrar_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionBDD.deleteGrupo(idGrupo);

                Toast.makeText(context, "El grupo se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PantallaPrincipal.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);

                // Actualizamos los que eran miembros del grupo para que se borre el grupo de su lista de grupos
                for (Integer idActualizar : miembros) {
                    ConexionBDD.actualizarUsuarioElimGrupo(idActualizar, idGrupo, new interfaces.OnUsuarioActualizadoListener() {
                        @Override
                        public void onUsuarioActualizado() {
                            Log.d("TAG", "Usuario actualizado correctamente");
                        }
                    });
                }
                //Para borrar los eventos votaciones y confirmaciones relacionados con ese grupo.
                ConexionBDD.borrarEventosDeGrupo(idGrupo);///
                for (int idEvento : idEventos) {
                    ConexionBDD.borrarEventosDeGrupo(idEvento);
                    ConexionBDD.borrarEncuestasDeEvento(idEvento);
                }
                for (int idEncuesta : idEncuestas) {
                    ConexionBDD.borrarVotacionesDeEncuesta(idEncuesta);
                }

            }
        });

        // Guardamos todas las modificaciones que hayamos hecho en el grupo
        btn_guardar_modif_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modifNombre = et_modif_nombre_grupo.getText().toString();
                String modifDescripcion = et_modif_descripcion_grupo.getText().toString();

                int colorSeleccionado = spn_modif_colores_grupo.getSelectedItemPosition();
                String color = String.valueOf(spn_modif_colores_grupo.getItemAtPosition(colorSeleccionado));

                ConexionBDD.actualizarGrupo(idGrupo, modifNombre, modifDescripcion, color, miembros);

                for (Integer idActualizar : miembros) {
                    ConexionBDD.actualizarUsuario(idActualizar, idGrupo, new interfaces.OnUsuarioActualizadoListener() {
                        @Override
                        public void onUsuarioActualizado() {
                            // Se ejecuta cuando el usuario ha sido actualizado correctamente
                            Log.d("TAG", "Usuario actualizado correctamente");
                        }
                    });
                }

                Intent intent = new Intent(context, PantallaPrincipal.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });
        
    }
    // Recoge el indice de color del grupo
    private int getIndiceColor(Grupo grupo) {

        int indiceColor = 0;

        switch (grupo.getColor()) {
            case "Rojo":
                indiceColor = 0;
                break;
            case "Verde":
                indiceColor = 1;
                break;
            case "Azul":
                indiceColor = 2;
                break;
            case "Cian":
                indiceColor = 3;
                break;
            case "Morado":
                indiceColor = 4;
                break;
            case "Amarillo":
                indiceColor = 5;
                break;

        }

        return indiceColor;
    }
    // Método que utiliza el botón de añadir miembros para llevarnos a la pantalla de añadir miembros al grupo
    public void ModifAnnadirMiembros(View view){

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
                        ConexionBDD.actualizarUsuario(idActualizar, idGrupo, new interfaces.OnUsuarioActualizadoListener() {
                            @Override
                            public void onUsuarioActualizado() {
                                // Se ejecuta cuando el usuario ha sido actualizado correctamente
                                Log.d("TAG", "Usuario actualizado correctamente");
                            }
                        });
                    }

                    ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                        @Override
                        public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                            adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                            lv_modif_miembros_annadidos.setAdapter(adapMiembros);
                            parentAdapter = lv_modif_miembros_annadidos.getAdapter();
                            Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                        }
                    });
                    adapMiembros.notifyDataSetChanged();
                }
            }
        }
    }

}
