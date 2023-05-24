package com.example.proyectofinaldam.gestion_grupos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.grupos.AdaptadorSpinnerColores;
import com.example.proyectofinaldam.grupos.grupo;

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
    private Button btn_modif_eliminar_miembro;
    private Button btn_modif_annadir_miembros;
    private ListView lv_modif_miembros_annadidos;
    private Button btn_modif_borrar_grupo;
    private AdaptadorMiembros adapMiembros;
    Context context;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.modificar_grupo);

        Intent intent = getIntent();
        String nomGrupo = intent.getStringExtra("nomGrupo");

        Log.d("TAG", "RECIBIMOS NOMGRUPO A MODIFICAR " + nomGrupo);

        context = getApplicationContext();

        // Obtener la referencia a los diferentes elementos del layout

        // Campo para modificar el nombre del grupo
        et_modif_nombre_grupo = (EditText) findViewById(R.id.et_modif_nombre_grupo);
        // Campo para modificar la descripción del grupo
        et_modif_descripcion_grupo = (EditText) findViewById(R.id.et_modif_descripcion_grupo);
        // Spinner de los colores del grupo
        spn_modif_colores_grupo = (Spinner) findViewById(R.id.spn_modif_color);
        // Boton eliminar miembro
        btn_modif_eliminar_miembro = (Button) findViewById(R.id.btn_modif_eliminar_miembro);
        // Boton añadir miembro que te lleva a la pantallita de añadir
        btn_modif_annadir_miembros = (Button) findViewById(R.id.btn_modif_annadir_miembros);
        // Listview que muestra los miembros del grupo
        lv_modif_miembros_annadidos = (ListView) findViewById(R.id.lv_modif_miembros_annadidos);
        // Boton borrar grupo
        btn_modif_borrar_grupo = (Button) findViewById(R.id.btn_modif_borrar_grupo);


        // Lista de los elementos del spinner de colores
        String[] elementos = {"Rojo", "Verde", "Azul", "Cian", "Morado", "Amarillo"};
        // Lista de colores del spinner de colores para pintar cada opción de un color
        int[] colores = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW};

        // Adaptador del spinner de colores
        AdaptadorSpinnerColores adapter = new AdaptadorSpinnerColores(this,
                android.R.layout.simple_spinner_item, elementos, colores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_modif_colores_grupo.setAdapter(adapter);

        ConexionBDD.obtenerGrupoPorNombre(nomGrupo, new VerGrupo.OnGrupoObtenidoListener() {

            @Override
            public void onGrupoObtenido(grupo grupo) {

                Log.d(TAG, "Llega hasta aqui? " + grupo);
                Log.d(TAG, "Pasa bien el nombre del grupo? " + nomGrupo);

                et_modif_nombre_grupo.setText(grupo.getNombre());
                et_modif_descripcion_grupo.setText(grupo.getDescripcion());

                List<Integer> miembros = grupo.getMiembros();

                int indiceColor = getIndiceColor(grupo);

                spn_modif_colores_grupo.setSelection(indiceColor);

                ConexionBDD.obtenerUsuariosPorIds(miembros, new VerGrupo.OnUsuariosObtenidosListener() {
                    @Override
                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                        adapMiembros = new AdaptadorMiembros(usuarios, context);
                        lv_modif_miembros_annadidos.setAdapter(adapMiembros);
                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                    }
                });
            }

        });

        btn_modif_borrar_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionBDD.deleteGrupo(nomGrupo);
                Intent intent = new Intent(context, PantallaPrincipal.class);
                startActivity(intent);
            }
        });
        
    }

    private int getIndiceColor(grupo grupo) {

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
        startActivity(intent2);

    }

    //Falta boton guardar que vuelva al Ver grupo
}
