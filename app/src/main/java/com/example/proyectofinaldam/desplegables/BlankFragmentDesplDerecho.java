package com.example.proyectofinaldam.desplegables;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.ConexionBDD;
import com.example.proyectofinaldam.PantallaPrincipal;
import com.example.proyectofinaldam.R;
import com.example.proyectofinaldam.evento.AdaptadorEvento;
import com.example.proyectofinaldam.interfaces;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

    private Spinner spn_Evento;
    private Spinner spn_Grupo;
    private Spinner spn_Confirmacion;

    private Button btn_filtrarDatos;

    private ListView lv_eventos;
    private AdaptadorEvento adaptadorLVEvento;
    ArrayList<String> nombresGrupos = new ArrayList<>();
    ArrayList<Integer> listaIdsGrupos = new ArrayList<>();

    int idUsuarioFragment;
    int idUsuario;

    int idGrupoSeleccionado;

    //valores del filtro
    int valorEvento;
    int idGrupo;
    int valorConfirmacion;



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
            idUsuario = getArguments().getInt("idUsuario", idUsuarioFragment);
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
        spn_Evento = (Spinner) view.findViewById(R.id.spn_dpsDer_eventoAbierto);
        spn_Grupo = (Spinner) view.findViewById(R.id.spn_eventos_grupo);
        spn_Confirmacion = (Spinner) view.findViewById(R.id.spn_eventos_encuestas);
        btn_filtrarDatos = (Button) view.findViewById(R.id.btn_filtrar);
        lv_eventos = (ListView) view.findViewById(R.id.lv_eventos_despl_derecho);
        ArrayList<Evento> listaEvento = new ArrayList<>();

        // Crear el array con los elementos cerrados y abiertos
        String[] elementos = {"Cerrado", "Abierto"};
        // Crear el adaptador con el array
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item , elementos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Crear el Spinner y asignarle el adaptador
        spn_Evento.setAdapter(adaptador);

        //Creamos el spinner de confirmacion
        String[] elementosConfirmacion = {"Eventos en curso", "Eventos Finalizados"};
        // Crear el adaptador con el array
        ArrayAdapter<String> adaptadorConf = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item , elementosConfirmacion);
        adaptadorConf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Crear el Spinner y asignarle el adaptador
        spn_Confirmacion.setAdapter(adaptadorConf);

        //Creamos el spinner de grupos.

        ConexionBDD.obtenerGruposPorMiembro(idUsuario, new interfaces.OnGruposObtenidosListener() {
            @Override
            public void onGruposObtenidos(ArrayList<Grupo> listaGrupos) {

                for (Grupo grupo : listaGrupos) {
                    String nombreGrupo = grupo.getNombre();
                    nombresGrupos.add(nombreGrupo);
                }
                for (Grupo grupo : listaGrupos){
                    Integer idGrupo = grupo.getId();
                    listaIdsGrupos.add(idGrupo);
                }

                // Crea un adaptador para el Spinner
                ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresGrupos);
                adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Asigna el adaptador al Spinner
                spn_Grupo.setAdapter(adaptadorSpinner);
            }
        });

        //Listener para saber el ID del grupo.
        spn_Grupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtén el nombre del grupo seleccionado
                String nombreGrupoSeleccionado = nombresGrupos.get(position);

                // Busca el índice correspondiente en la lista de nombres de grupos
                int indiceGrupoSeleccionado = nombresGrupos.indexOf(nombreGrupoSeleccionado);

                // Obtén el ID del grupo correspondiente al nombre seleccionado
                idGrupoSeleccionado = listaIdsGrupos.get(indiceGrupoSeleccionado);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Implementa este método si deseas realizar alguna acción cuando no se seleccione ningún elemento del spinner
            }
        });


        btn_filtrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui habra que recoger los datos seleccionados del spinner para mostrarlos en el ListView.
                valorEvento = spn_Evento.getSelectedItemPosition(); // Cerrado = 0, Abierto = 1
                idGrupo = idGrupoSeleccionado; // Obtener el ID del grupo seleccionado
                valorConfirmacion = spn_Confirmacion.getSelectedItemPosition(); // No confirmado = 0, Si confirmado = 1
                Log.d("spinnerEvento", "La posicion del evento es " + valorEvento + idGrupo + valorConfirmacion);
                //Hacemos el select con los datos recogidos anteriormente.

                ConexionBDD.EventoLVDerecho(valorEvento, idGrupo, valorConfirmacion, new interfaces.OnEventosObtenidosListener() {
                    @Override
                    public void onEventosObtenidos(ArrayList<Evento> listaEvento) {

                        adaptadorLVEvento = new AdaptadorEvento(listaEvento, idUsuario, valorEvento, idGrupo, getContext());

                        lv_eventos.setAdapter(adaptadorLVEvento);

                        Log.d("TAG", "Número de eventos obtenidos: " + listaEvento.size());
                    }


                });

            }
        });




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

