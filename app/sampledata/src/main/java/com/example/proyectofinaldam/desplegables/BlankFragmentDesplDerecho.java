package com.example.proyectofinaldam.desplegables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentDesplDerecho#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentDesplDerecho extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentDesplDerecho() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentDesplDerecho.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentDesplDerecho newInstance(String param1, String param2) {
        BlankFragmentDesplDerecho fragment = new BlankFragmentDesplDerecho();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_despl_derecho, container, false);

        // Obtener la referencia a los diferentes elementos del layout

        // Botón para plegar el desplegable
        ImageButton ib_plegar_despl_derecho = view.findViewById(R.id.ib_plegar_despl_derecho);
        // Los 3 botones que hay en la pantalla principal que tenemos que hacer desaparecer cuando desplegamos el desplegable
        Button btn_despl_izquierdo = getActivity().findViewById(R.id.btn_despl_izquierdo);
        Button btn_despl_derecho = getActivity().findViewById(R.id.btn_despl_derecho);
        FloatingActionButton btn_calendario_crear_evento = getActivity().findViewById(R.id.btn_calendario_crear_evento);


        // Acción que realiza el botón de plegar el desplegable que está abierto
         ib_plegar_despl_derecho.setOnClickListener(new View.OnClickListener() {
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
                    }

                    // Simular la pulsación del botón "Back" en la actividad principal
                    getActivity().onBackPressed();
                }

            });

            return view;
        }

    }

