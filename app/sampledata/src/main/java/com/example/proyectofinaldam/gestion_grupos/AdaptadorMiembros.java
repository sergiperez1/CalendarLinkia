package com.example.proyectofinaldam.gestion_grupos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.R;

import java.util.ArrayList;

public class AdaptadorMiembros extends BaseAdapter {

    // Muestra los nombres de los miembros del grupo
    ArrayList<Usuario> listaUsuarios;
    TextView tv_nombre_miembro;
    View vistaElemento;
    Context context;

    public AdaptadorMiembros(ArrayList<Usuario> listaUsuarios, Context context){

        this.listaUsuarios = listaUsuarios;
        this.context = context;

    }

    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflador = LayoutInflater.from(context);
        vistaElemento = inflador.inflate(R.layout.item_lista_miembros_grupo, viewGroup, false);

        Usuario usuario = listaUsuarios.get(i);

        // Configurar el TextView
        tv_nombre_miembro = vistaElemento.findViewById(R.id.tv_nombre_miembro);
        tv_nombre_miembro.setText(usuario.getNombre());

        vistaElemento.setTag(usuario.getId());


        //////////////////////////////////////////////////////////////////////

        /*vistaElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos un intent para iniciar la activity correspondiente
                Intent intent = new Intent(context, VerGrupo.class);

                //Pasamos el nombre del grupo como extra
                intent.putExtra("nomGrupo", grupo.getNombre());

                //Iniciamos la activity
                context.startActivity(intent);
            }
        });*/

        return vistaElemento;
    }
}
