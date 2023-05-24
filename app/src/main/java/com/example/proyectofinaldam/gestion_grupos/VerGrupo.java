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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.interfaces;

import java.util.ArrayList;
import java.util.List;

public class VerGrupo extends AppCompatActivity {
    //Cuando seleccionas el listview se haga un select con toda la informacion del grupo.


    Context context;
    private TextView tv_ver_nombre_grupo;
    private TextView tv_ver_descrip_grupo;
    private ImageView imageView2;
    private ListView lv_ver_miembros_grupo;
    private Button btn_modificar_grupo;
    private AdaptadorMiembros adapMiembros;
    private int idUsuario;
    private int idGrupo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_grupo);

        Intent intent = getIntent();
        idUsuario = getIntent().getIntExtra("idUsuario", idUsuario);
        String nomGrupo = intent.getStringExtra("nomGrupo");
        idGrupo = getIntent().getIntExtra("idGrupo", idGrupo);

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

        // Obtenemos el grupo a través del id que tenemos y llenamos todos los campos con sus características
        ConexionBDD.obtenerGrupoPorId(idGrupo, new OnGrupoObtenidoListener() {
            @Override
            public void onGrupoObtenido(Grupo grupo) {
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
                Log.d("TAG", "Número de miembros obtenidos 1: " + miembros);

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
                ConexionBDD.esAdminGrupo(idUsuario, idGrupo, new interfaces.OnCheckFieldListener() {
                    @Override
                    public void onFieldExists(boolean exists) {
                        if (exists) {
                            Intent intent = new Intent(getApplicationContext(), ModificarGrupo.class);
                            //Pasamos el nombre del grupo y el id del usuario como extra
                            intent.putExtra("idUsuario", idUsuario);
                            intent.putExtra("idGrupo", idGrupo);
                            intent.putExtra("nomGrupo", nomGrupo);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "Tienes que ser administrador para modificar el grupo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

    }

    public interface OnGrupoObtenidoListener {
        void onGrupoObtenido(Grupo grupo);
    }

    public interface OnUsuarioObtenidoListener {
        void onUsuarioObtenido(Usuario usuario);
    }

    public interface OnUsuariosObtenidosListener {
        void onUsuariosObtenidos(ArrayList<Usuario> usuarios);
    }


}
