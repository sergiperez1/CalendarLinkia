package com.example.proyectofinaldam.gestion_grupos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;

import java.util.ArrayList;

public class AnnadirMiembros extends AppCompatActivity {

    // Declaramos todos los elementos del layout que necesitaremos
     private EditText etBuscador;
     private ImageButton ibBuscarEmpleado;
     private ListView lv_usuarios_buscados;
     private Button btn_annadir_usuario;
     private Button btn_volver_annadir;
     private AdaptadorMiembros adapMiembros;
     int posicionSeleccionada;
     private int idUserAAnnadir;
     ListAdapter parentAdapter;
     private ArrayList<Integer> miembros;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annadir_miembros);

        // Recogemos el array de miembros de la pantalla anterior
        miembros = getIntent().getIntegerArrayListExtra("miembros");

        // Declaramos la referencia de los elementos del layout

        // Campo para buscar el nombre del usuario
        etBuscador = (EditText) findViewById(R.id.etBuscador);
        // Boton con la Imagen de la lupa para hacer la busqueda
        ibBuscarEmpleado = (ImageButton) findViewById(R.id.ibBuscarEmpleado);
        // Listview que muestra los usuarios resultado de la busqueda
        lv_usuarios_buscados = (ListView) findViewById(R.id.lv_usuarios_buscados);
        // Boton para añadir el usuario al grupo
        btn_annadir_usuario = (Button) findViewById(R.id.btn_annadir_usuario);
        btn_volver_annadir = (Button) findViewById(R.id.btn_volver_annadir);


        // Cuando apretamos el boton de la lupa se hace la busqueda del usuario por el nombre introducido
        ibBuscarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = etBuscador.getText().toString();

                // Obtenemos los resultados de la busqueda por el nombre y los mostramos en el listview
                ConexionBDD.obtenerUsuariosPorNombre(busqueda, new VerGrupo.OnUsuariosObtenidosListener() {
                    @Override
                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                        adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                        lv_usuarios_buscados.setAdapter(adapMiembros);
                        parentAdapter = lv_usuarios_buscados.getAdapter();
                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                    }
                });
            }
        });


        // Cuando seleccionamos un usuario del listview, se sombrea y se queda seleccionado.
        // Definimos el valor de idUserAAnnadir como el id del usuario seleccionado
        lv_usuarios_buscados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicionSeleccionada = position;
                view.setSelected(true);

                Object objetoSeleccionado = parentAdapter.getItem(posicionSeleccionada);

                if(objetoSeleccionado instanceof Usuario){
                    Usuario usuario = (Usuario) objetoSeleccionado;
                    idUserAAnnadir = usuario.getId();
                    Log.d("TAG", "ID DEL MIEMBRO A AÑADIR " + idUserAAnnadir);
                }
            }
        });


        // Acción del botón que añade el usuario al grupo
        btn_annadir_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(miembros.contains(idUserAAnnadir)){
                    Toast.makeText(AnnadirMiembros.this, "El usuario ya está en el grupo", Toast.LENGTH_SHORT).show();
                }else {

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("idUserAAnnadir", idUserAAnnadir);
                    Log.d("TAG", "ID DEL MIEMBRO A AÑADIR 2: " + idUserAAnnadir);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        btn_volver_annadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}
