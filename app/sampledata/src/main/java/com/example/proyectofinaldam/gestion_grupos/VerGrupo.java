package com.example.proyectofinaldam.gestion_grupos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.grupos.grupo;

import java.util.ArrayList;
import java.util.List;

public class VerGrupo extends AppCompatActivity {
    //Esta pantalla se tiene que poner al clicar en el list View.

    //Cuando seleccionas el listview se haga un select con toda la informacion del grupo.


    Context context;
    private TextView tv_ver_nombre_grupo;
    private TextView tv_ver_descrip_grupo;
    private ImageView imageView2;
    private ListView lv_ver_miembros_grupo;
    private Button btn_modificar_grupo;
    private AdaptadorMiembros adapMiembros;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_grupo);

        Intent intent = getIntent();
        String nomGrupo = intent.getStringExtra("nomGrupo");

        context = getApplicationContext();

        // Identificamos todos los elementos del layout que nos serán necesarios:

        // Texto donde se nos mostrará el nombre del grupo
        tv_ver_nombre_grupo = (TextView) findViewById(R.id.tv_ver_nombre_grupo);
        // Texto donde se nos muestra la descripción del grupo
        tv_ver_descrip_grupo = (TextView) findViewById(R.id.tv_ver_descrip_grupo);
        // ImageView del color del cual es el grupo que estamos viendo
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        // ListView que nos muestra la lista de miembros del grupo
        lv_ver_miembros_grupo = (ListView) findViewById(R.id.lv_ver_miembros_grupo);
        // Botón para llevarnos a la pantalla de modificar el grupo
        btn_modificar_grupo = (Button) findViewById(R.id.btn_modificar_grupo);

        ConexionBDD.obtenerGrupoPorNombre(nomGrupo, new OnGrupoObtenidoListener() {
            @Override
            public void onGrupoObtenido(grupo grupo) {
                tv_ver_nombre_grupo.setText(grupo.getNombre());
                tv_ver_descrip_grupo.setText(grupo.getDescripcion());

                switch (grupo.getColor()) {
                    case "Rojo":
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.Rojo));
                        break;
                    case "Verde":
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.Verde));
                        break;
                    case "Azul":
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.Azul));
                        break;
                    case "Cian":
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.Cian));
                        break;
                    case "Morado":
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.Morado));
                        break;
                    case "Amarillo":
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.Amarillo));
                        break;
                    default:
                        imageView2.setColorFilter(ContextCompat.getColor(context, R.color.black));
                }

                imageView2.setImageResource(R.drawable.circle_shape);

                List<Integer> miembros = grupo.getMiembros();

                ConexionBDD.obtenerUsuariosPorIds(miembros, new OnUsuariosObtenidosListener() {
                    @Override
                    public void onUsuariosObtenidos(ArrayList<Usuario> usuarios) {
                        adapMiembros = new AdaptadorMiembros(usuarios, context);
                        lv_ver_miembros_grupo.setAdapter(adapMiembros);
                        Log.d("TAG", "Número de miembros obtenidos: " + usuarios.size());
                    }
                });

            }
        });




        // Función que nos lleva a la pantalla de modificar el grupo a través del botón Modificar
        btn_modificar_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModificarGrupo.class);
                //Pasamos el nombre del grupo como extra
                intent.putExtra("nomGrupo", nomGrupo);
                startActivity(intent);

            }
        });

    }

    public interface OnGrupoObtenidoListener {
        void onGrupoObtenido(grupo grupo);
    }

    public interface OnUsuarioObtenidoListener {
        void onUsuarioObtenido(Usuario usuario);
    }

    public interface OnUsuariosObtenidosListener {
        void onUsuariosObtenidos(ArrayList<Usuario> usuarios);
    }


}
