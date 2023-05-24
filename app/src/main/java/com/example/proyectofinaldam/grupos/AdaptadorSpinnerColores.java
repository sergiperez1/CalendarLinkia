package com.example.proyectofinaldam.grupos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Adaptador para el desplegable de colores
public class AdaptadorSpinnerColores extends ArrayAdapter<String> {

    private int[] colores;

    public AdaptadorSpinnerColores(Context context, int resource, String[] objects, int[] colores){

        super(context, resource, objects);
        this.colores = colores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setBackgroundColor(colores[position]);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setBackgroundColor(colores[position]);
        return view;
    }

}
