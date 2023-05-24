package com.example.proyectofinaldam.grupos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.gestion_grupos.VerGrupo;

import java.util.ArrayList;

public class AdaptadorGrupos extends BaseAdapter  {
    //Funcion que haga un select que muestre los grupos(nombre, color).
    ArrayList<Grupo> listaGrupos;
    View vistaElemento;
    private int idUsuario;
    Context context;
    ArrayList<Grupo> gruposChecked;

    public AdaptadorGrupos(ArrayList<Grupo> listaGrupos, Context context, int idUsuario){

        this.listaGrupos = listaGrupos;
        this.context = context;
        this.idUsuario = idUsuario;
        this.gruposChecked = new ArrayList<>();

    }
    @Override
    public int getCount() {
        return listaGrupos.size();
    }

    public ArrayList<Grupo> getGruposChecked(){
        return gruposChecked;
    }

    @Override
    public Object getItem(int position) {
        return listaGrupos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflador = LayoutInflater.from(context);
        vistaElemento = inflador.inflate(R.layout.listview_grupos, viewGroup, false);

        // Referenciamos la vista del elemento
        ImageView circleView = vistaElemento.findViewById(R.id.circle);

        Grupo grupo = listaGrupos.get(i);

        switch (grupo.getColor()) {
            case "Rojo":
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.Rojo));
                break;
            case "Verde":
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.Verde));
                break;
            case "Azul":
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.Azul));
                break;
            case "Cian":
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.Cian));
                break;
            case "Morado":
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.Morado));
                break;
            case "Amarillo":
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.Amarillo));
                break;
            default:
                circleView.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }

        circleView.setImageResource(R.drawable.circle_shape);

            // Configuramos el TextView
            TextView tvNombreGrupo = vistaElemento.findViewById(R.id.tv_grupo);
            //grupo grupo = listaGrupos.get(i);
            tvNombreGrupo.setText(grupo.getNombre());

            // Configuramos el RadioButton
            CheckBox cb_grupo = vistaElemento.findViewById(R.id.cb_grupo);

            cb_grupo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Grupo grupoMarcado = listaGrupos.get(i);
                    if(isChecked){
                        gruposChecked.add(grupoMarcado);
                        Log.d("TAG", "Grupos marcados: " + gruposChecked.size());
                    }else{
                        gruposChecked.remove(grupoMarcado);
                        Log.d("TAG", "Grupos marcados: " + gruposChecked.size());
                    }
                }
            });

        Log.d("TAG", "Grupos marcados: " + gruposChecked.size());

            vistaElemento.setTag(grupo.getNombre());

            vistaElemento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creamos un intent para iniciar la activity correspondiente
                    Intent intent = new Intent(context, VerGrupo.class);

                    //Pasamos el nombre del grupo y el id usuario como extra
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("nomGrupo", grupo.getNombre());
                    intent.putExtra("idGrupo", grupo.getId());

                    //Iniciamos la activity
                    context.startActivity(intent);
                }
            });

            return vistaElemento;
        }


}
