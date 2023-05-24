package com.example.proyectofinaldam.evento;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.R;

import java.util.ArrayList;

public class AdaptadorEvento extends BaseAdapter {

    ArrayList<Evento> listaEvento;
    View vistaElemento;
    private int idUsuario;

    private int valorEvento;
    private int idGrupo;
    TextView tv_nombreEvento;

    Context context;

    public AdaptadorEvento(ArrayList<Evento> listaEvento, int idUsuario, int valorEvento, int idGrupo, Context context) {
        this.listaEvento = listaEvento;
        this.idUsuario = idUsuario;
        this.context = context;
        this.valorEvento = valorEvento;
        this.idGrupo = idGrupo;
    }

    @Override
    public int getCount() {
        return listaEvento.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEvento.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflador = LayoutInflater.from(context);
        vistaElemento = inflador.inflate(R.layout.eventos_lista, viewGroup, false);

        Evento evento = listaEvento.get(position);

        //Configuramos el textView.
        tv_nombreEvento = vistaElemento.findViewById(R.id.tv_nombre_evento_dspl_Derecho);
        String nombre = evento.getNombre().toString();

        tv_nombreEvento.setText(nombre);
        String nombreEvento = evento.getNombre();

        Integer fk_idGrupo = idGrupo;
        Integer idEvento = evento.getId();
        vistaElemento.setTag(evento.getId());

        vistaElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valorEvento == 0) {
                    //Creamos un intent para iniciar la activity correspondiente
                    Intent intent = new Intent(context, EventoCerradoDescripcion.class);

                    //Pasamos el nombre del grupo y el id usuario como extra
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("nomEvento", nombreEvento);
                    intent.putExtra("idGrupo", fk_idGrupo);
                    intent.putExtra("idEvento", idEvento);
                    //Iniciamos la activity
                    context.startActivity(intent);

                } else if(valorEvento == 1){
                    //Creamos un intent para iniciar la activity correspondiente
                    Intent intent2 = new Intent(context, EventoAbiertoDescripcion.class);

                    //Pasamos el nombre del grupo y el id usuario como extra
                    intent2.putExtra("idUsuario", idUsuario);
                    intent2.putExtra("nomEvento", nombreEvento);
                    intent2.putExtra("idGrupo", fk_idGrupo);
                    intent2.putExtra("idEvento", idEvento);

                    //Iniciamos la activity
                    context.startActivity(intent2);
                }
            }
        });

        return vistaElemento;


    }
}
