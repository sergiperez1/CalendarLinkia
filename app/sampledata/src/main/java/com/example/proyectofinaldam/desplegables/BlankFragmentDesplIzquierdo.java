package com.example.proyectofinaldam.desplegables;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.gestion_grupos.CrearGrupo;
import com.example.proyectofinaldam.grupos.AdaptadorGrupos;
import com.example.proyectofinaldam.grupos.grupo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentDesplIzquierdo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentDesplIzquierdo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView btn_crear_grupo;
    private ListView lv_grupos;
    private TextView btn_mostrar_eventos;
    private AdaptadorGrupos adaptador;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int idUsuarioFragment;
    int idUsuario;

    public BlankFragmentDesplIzquierdo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentDesplIzquierdo.
     */
    // TODO: Rename and change types and number of parameters
    public BlankFragmentDesplIzquierdo newInstance(String param1, String param2) {
        BlankFragmentDesplIzquierdo fragment = new BlankFragmentDesplIzquierdo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            idUsuario = getArguments().getInt("idUsuario" , idUsuarioFragment);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_despl_izquierdo, container, false);

        // Obtener la referencia a los diferentes elementos del layout

        // Boton que nos lleva a la pagina de crear un grupo nuevo
        TextView btn_crear_grupo = view.findViewById(R.id.btn_crear_grupo);
        TextView btn_mostrar_eventos = view.findViewById(R.id.btn_mostrar_eventos);
        // Botón para plegar el desplegable
        ImageButton ib_plegar_despl_izq = view.findViewById(R.id.ib_plegar_despl_izq);
        // Los 3 botones que hay en la pantalla principal que tenemos que hacer desaparecer cuando desplegamos el desplegable
        Button btn_despl_izquierdo = getActivity().findViewById(R.id.btn_despl_izquierdo);
        Button btn_despl_derecho = getActivity().findViewById(R.id.btn_despl_derecho);
        FloatingActionButton btn_calendario_crear_evento = getActivity().findViewById(R.id.btn_calendario_crear_evento);

        lv_grupos = view.findViewById(R.id.lv_grupos_desplegable);

        ArrayList<grupo> listaGrupos = new ArrayList<>();

        ConexionBDD.obtenerGruposPorMiembro(idUsuario, new OnGruposObtenidosListener() {
            @Override
            public void onGruposObtenidos(ArrayList<grupo> listaGrupos) {
                adaptador = new AdaptadorGrupos(listaGrupos, getContext());
                lv_grupos.setAdapter(adaptador);
                Log.d("TAG", "Número de grupos obtenidos: " + listaGrupos.size());
            }
        });

        // Acción que realiza el botón de crear un grupo nuevo, es decir nos lleva a la pantalla de creación
        btn_crear_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para ir a la otra actividad
                Intent irCrearGrupo = new Intent(getActivity(), CrearGrupo.class);
                irCrearGrupo.putExtra("ID_USUARIO", idUsuario);
                // Iniciar la otra actividad
                startActivity(irCrearGrupo);
            }
        });


        btn_mostrar_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AQUI TENEMOS QUE MOSTRAR EN EL CALENDARIO

            }
        });

        // Acción que realiza el botón de plegar el desplegable que está abierto
        ib_plegar_despl_izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el fragment del desplegable
                getView().setVisibility(View.GONE);

                // Mostrar los botones en la actividad principal (a la hora de desplegar el desplegable los habíamos ocultado)
                if (getActivity() instanceof PantallaPrincipal) {
                    PantallaPrincipal activity = (PantallaPrincipal) getActivity();
                    boolean mostrarBotones = activity.mostrarBotones;
                    btn_despl_izquierdo.setVisibility(mostrarBotones ? View.VISIBLE : View.GONE);
                    btn_despl_derecho.setVisibility(mostrarBotones ? View.VISIBLE : View.GONE);
                    btn_calendario_crear_evento.setVisibility(mostrarBotones ? View.VISIBLE : View.GONE);
                }
            }
        });

        return view;
    }



    public interface OnGruposObtenidosListener {
        void onGruposObtenidos(ArrayList<grupo> listaGrupos);
    }

}