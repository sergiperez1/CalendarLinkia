package com.example.proyectofinaldam.gestion_grupos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;

import java.util.ArrayList;

public class AnnadirMiembros extends AppCompatActivity {

     private EditText etBuscador;
     private ImageButton ibBuscarEmpleado;
     private ListView lv_usuarios_buscados;
     private Button btn_annadir_usuario;
     private AdaptadorMiembros adapMiembros;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annadir_miembros);

        // Obtener la referencia a los diferentes elementos del layout

        // Campo para buscar el nombre del usuario
        etBuscador = (EditText) findViewById(R.id.etBuscador);
        // Boton con la Imagen de la lupa para hacer la busqueda
        ibBuscarEmpleado = (ImageButton) findViewById(R.id.ibBuscarEmpleado);
        // Listview que muestra los usuarios resultado de la busqueda
        lv_usuarios_buscados = (ListView) findViewById(R.id.lv_usuarios_buscados);
        // Boton para añadir el usuario al grupo
        btn_annadir_usuario = (Button) findViewById(R.id.btn_volver_annadir);


        ibBuscarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = etBuscador.getText().toString();

                ConexionBDD.obtenerUsuariosPorNombre(busqueda, new VerGrupo.OnUsuariosObtenidosListener() {
                    @Override
                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                        adapMiembros = new AdaptadorMiembros(usuarios, getApplicationContext());
                        lv_usuarios_buscados.setAdapter(adapMiembros);
                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                    }
                });
            }
        });


        // Acción del botón que añade el usuario al grupo
        btn_annadir_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
