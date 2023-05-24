package com.example.proyectofinaldam;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.desplegables.BlankFragmentDesplIzquierdo;
import com.example.proyectofinaldam.gestion_grupos.VerGrupo;
import com.example.proyectofinaldam.grupos.grupo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConexionBDD extends AppCompatActivity {
        private static FirebaseFirestore db;
        public static final String TAG = "ConexionBDD";
        //Funcion para conectarse a la bbdd.
        public static FirebaseFirestore getFirestore(Context context) {
            if (db == null) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setProjectId("proyectofinaldam-1bb51")
                        .setApplicationId("1:21646887236:android:b216b5accb841921c16f11")
                        .setApiKey("AIzaSyDEuaDtVmd-LaJW9iqg0Mng0nktoqfE0Ak")
                        .build();

                db = FirebaseFirestore.getInstance();
            }
            return db;
        }
    // Se inicializa Firebase
    public void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
    }



    //Funcion que compruebe el nombre de la tabla usuario si existe.
    //El valor de Nombre es el Campo de la coleccion Usuario.
    public void comprobarNombre(String collectionName, String CampoColeccion, String fieldValue, interfaces.OnCheckFieldListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(collectionName);

        Query query = collectionRef.whereEqualTo(CampoColeccion, fieldValue);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    Log.d(TAG, "El nombre de usuario existe!");
                    listener.onFieldExists(true); // Si el campo existe, se llama al listener con true
                } else {
                    Log.d(TAG, "El nombre de usuario no existe!.");
                    listener.onFieldExists(false); // Si el campo no existe, se llama al listener con false
                }
            } else {
                Log.e(TAG, "Error al comprobar la contraseña de usuario en la base de datos: " + task.getException());
                listener.onFieldExists(false); // Si se produce un error, se llama al listener con false
            }
        });
    }

    //Funcion que compruebe el password de la tabla usuario si existe

    public void comprobarContrasena(String collectionName, String CampoColeccion, String fieldValue, interfaces.OnCheckFieldListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(collectionName);

        Query query = collectionRef.whereEqualTo(CampoColeccion, fieldValue);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    Log.d(TAG, "La contraseña de usuario existe!");
                    listener.onFieldExists(true); // Si el campo existe, se llama al listener con true
                } else {
                    Log.d(TAG, "La contraseña de usuario no existe!.");
                    listener.onFieldExists(false); // Si el campo no existe, se llama al listener con false
                }
            } else {
                Log.e(TAG, "Error al comprobar el nombre de usuario en la base de datos: " + task.getException());
                listener.onFieldExists(false); // Si se produce un error, se llama al listener con false
            }
        });
    }
    //Funcin que actualiza la contraseña a la que el usuario indica en el usuario que el correo obtenga.
    public static void actualizarPassword(String correo, String nuevaPassword) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");

        // Realizar una consulta para obtener el documento correspondiente al correo
        Query query = usuariosRef.whereEqualTo("Correo", correo);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Obtener el ID del documento encontrado
                    String documentoId = querySnapshot.getDocuments().get(0).getId();

                    // Crear un mapa con el campo y el nuevo valor a actualizar
                    Map<String, Object> actualizacion = new HashMap<>();
                    actualizacion.put("Password", nuevaPassword);

                    // Actualizar el documento con el nuevo valor de Password
                    usuariosRef.document(documentoId)
                            .update(actualizacion)
                            .addOnSuccessListener(aVoid -> {
                                // Actualización exitosa
                                Log.d(TAG, "Se actualizó la contraseña correctamente.");
                            })
                            .addOnFailureListener(e -> {
                                // Error al actualizar
                                Log.e(TAG, "Error al actualizar la contraseña: " + e.getMessage());
                            });
                } else {
                    // No se encontró ningún documento con el correo especificado
                    Log.d(TAG, "No se encontró ningún usuario con el correo especificado.");
                }
            } else {
                // Error al realizar la consulta
                Log.e(TAG, "Error al consultar la base de datos: " + task.getException().getMessage());
            }
        });
    }

    //Funcion comprobarCorreo. SI existe en la BBDD el correo.

    public void comprobarCorreo(String collectionName, String CampoColeccion, String fieldValue, interfaces.OnCheckFieldListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(collectionName);

        Query query = collectionRef.whereEqualTo(CampoColeccion, fieldValue);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    Log.d(TAG, "El correo de usuario existe!");
                    listener.onFieldExists(true); // Si el campo existe, se llama al listener con true
                } else {
                    Log.d(TAG, "El correo de usuario no existe!.");
                    listener.onFieldExists(false); // Si el campo no existe, se llama al listener con false
                }
            } else {
                Log.e(TAG, "Error al comprobar el correo en la base de datos: " + task.getException());
                listener.onFieldExists(false); // Si se produce un error, se llama al listener con false
            }
        });
    }
    /**
     * getIDnewUsuario. Se usará al registrar usuario.
     * Funcion que hace un select en la coleccion usuarios y recoje el valor Del ultimo ID y le suma +1 si el select es correcto.
     * Si el select es correcto y la coleccion existe pero no hay valores el valor que retorne sera 1.
     * Si hay error en la conexion con la bbdd el valor sera -1.
     */
    public static void getIDnewUsuario(final interfaces.OnGetNextIdValueListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");

        usuariosRef
                .orderBy("Id", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            int maxId = documentSnapshot.getLong("Id").intValue();
                            listener.onGetNextIdValue(maxId + 1);
                        } else {
                            // No hay documentos en la colección, por lo tanto, el próximo valor de Id será 1.
                            listener.onGetNextIdValue(1);
                        }
                    } else {
                        // Error al acceder a la base de datos
                        listener.onGetNextIdValue(-1);
                    }
                });
    }

    /**
     * Funcion getIDusuario. Devuelva el ID del usuario del nombre que ha puesto al hacer login.
     */
    public static void getIdUsuario(String nombreUsuario, final interfaces.OnGetIdUsuarioListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");

        usuariosRef
                .whereEqualTo("Nombre", nombreUsuario)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Se encontró el usuario con el nombre indicado
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            Integer idUsuario = documentSnapshot.getLong("Id").intValue();
                            listener.onGetIdUsuario(idUsuario);
                        } else {
                            // No se encontró el usuario con el nombre indicado
                            listener.onGetIdUsuario(null);
                        }
                    } else {
                        // Error al acceder a la base de datos
                        listener.onGetIdUsuario(null);
                    }
                });
    }

    /**
     * Funcion RegistrarUsuario. Recoge los valors int, string, string, string. Referentes a id, correo, nombre, password.
     */
    public static void RegistrarUsuario(int Id, String correo, String nombre, String password, List<Integer> grupos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");

        // Crear un nuevo mapa con los datos del usuario
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("Id", Id);
        usuario.put("Correo", correo);
        usuario.put("Nombre", nombre);
        usuario.put("Password", password);
        usuario.put("grupos", grupos); // Campo grupos inicializado en null

        // Insertar el nuevo documento en la colección "Usuarios"
        usuariosRef.add(usuario)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Documento insertado con ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al insertar documento: " + e.getMessage());
                });
    }

    /**
     * Funciones de Crear grupo. Tendra la funcion getIdGrupo, CreaGrupo.
     */
    //Se utiliza al crear un grupo y se le suma +1 al Ultimo id del grupo.
    public static void getIdnewGrupo(final interfaces.OnGetNextIdValueListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        gruposRef
                .orderBy("Id", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            int maxId = documentSnapshot.getLong("Id").intValue();
                            listener.onGetNextIdValue(maxId + 1);
                        } else {
                            // No hay documentos en la colección, por lo tanto, el próximo valor de Id será 1.
                            listener.onGetNextIdValue(1);
                        }
                    } else {
                        // Error al acceder a la base de datos
                        listener.onGetNextIdValue(-1);
                    }
                });
    }
    /**
     * Funcion crearGrupo. Recogera los valores del Id(int), Nombre, Descripcion, color, miembros, administrador (int).
     */
    public static void crearGrupo (int Id, String nombre, String descripcion, String color, List<Integer> miembros, int administrador, String creacion) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference grupoRef = db.collection("Grupos");

        // Crear un nuevo mapa con los datos del grupo
        Map<String, Object> grupo = new HashMap<>();
        grupo.put("Id", Id);
        grupo.put("Nombre", nombre);
        grupo.put("Descripcion", descripcion);
        grupo.put("Color", color);
        grupo.put("Miembros", miembros); // Campo grupos inicializado en null
        grupo.put("Administrador" , administrador); //id del creador.
        grupo.put("Creacion", creacion);//creacion

        // Insertar el nuevo documento en la colección "Usuarios"
        grupoRef.add(grupo)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Documento insertado con ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al insertar documento: " + e.getMessage());
                });
    }


    public static void obtenerGruposPorMiembro(int idUsuario, BlankFragmentDesplIzquierdo.OnGruposObtenidosListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereArrayContains("Miembros", idUsuario);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<grupo> listaGrupos = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // obtiene los datos del documento y crea un objeto grupo
                        String grupoNombre = document.getString("Nombre");
                        String grupoColor = document.getString("Color");
                        grupo grupo = new grupo(grupoNombre, grupoColor);
                        listaGrupos.add(grupo);
                    }
                    listener.onGruposObtenidos(listaGrupos);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


    public static void obtenerGrupoPorNombre(String nomGrupo, VerGrupo.OnGrupoObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereEqualTo("Nombre", nomGrupo);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // obtiene los datos del documento y crea un objeto grupo
                        String grupoNombre = document.getString("Nombre");
                        String grupoDesc = document.getString("Descripcion");
                        String grupoColor = document.getString("Color");
                        List<Long> grupoMiembros = (List<Long>) document.get("Miembros");

                        // Convertir la lista de Long a lista de Integer
                        List<Integer> grupoMiembrosInteger = new ArrayList<>();
                        for (Long miembro : grupoMiembros) {
                            grupoMiembrosInteger.add(miembro.intValue());
                        }

                        grupo grupo = new grupo(grupoNombre, grupoDesc, grupoColor, grupoMiembrosInteger);
                        Log.d(TAG, "Los id de los miembros del grupo: " + grupoNombre + " son :" + grupoMiembrosInteger);

                        // notifica al listener que el grupo ha sido obtenido exitosamente
                        listener.onGrupoObtenido(grupo);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

        });
    }

    public static void obtenerUsuarioPorId(int idUsuario, VerGrupo.OnUsuarioObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuarioRef = db.collection("Usuarios");

        Log.d(TAG, "Aquí llega bien el id del usuario? " + idUsuario);

        Query query = usuarioRef.whereEqualTo("Id", idUsuario);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "La task es successful");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto Usuario
                        int id = document.getLong("Id").intValue();
                        String nombre = document.getString("Nombre");
                        String correo = document.getString("Correo");
                        String password = document.getString("Password");

                        Usuario usuario = new Usuario(id, nombre, correo, password);

                        Log.d(TAG, "Ya está creado el usuario : " + usuario);

                        // Notificar al listener que el usuario ha sido obtenido exitosamente
                        listener.onUsuarioObtenido(usuario);
                    }
                } else {
                        Log.d(TAG, "No encuentra registros");
                }

            }
        });
    }


    // Función para obtener un array de Usuario a partir de un array de Integer con los IDs de los usuarios
    public static void obtenerUsuariosPorIds(List<Integer> listaIdsUsuarios, VerGrupo.OnUsuariosObtenidosListener listener) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Log.d(TAG, "Los usuarios del grupo son: " + listaIdsUsuarios);
        final int totalUsuarios = listaIdsUsuarios.size();
        int contador = 0;

        for (int idUsuario : listaIdsUsuarios) {
            Log.d(TAG, "El usuario que se trata es: " + idUsuario);
            obtenerUsuarioPorId(idUsuario, new VerGrupo.OnUsuarioObtenidoListener() {
                int contadorFinal = contador;
                @Override
                public void onUsuarioObtenido(Usuario usuario) {
                    Log.d(TAG, "Llega bien el usuario? " + usuario.getId());

                    usuarios.add(usuario);
                    contadorFinal++;
                    // Si ya se han obtenido todos los usuarios, notificar al listener que se han obtenido exitosamente
                    if (contadorFinal == totalUsuarios) {
                        Log.d(TAG, "Pasamos al Listview? " + usuarios);
                        listener.onUsuariosObtenidos(usuarios);
                    }
                }
            });
        }
    }


    public static void obtenerUsuariosPorNombre(String nomUser, VerGrupo.OnUsuariosObtenidosListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuarioRef = db.collection("Usuarios");

        Log.d(TAG, "Aquí llega bien el nombre del usuario? " + nomUser);

        Query query = usuarioRef.whereEqualTo("Nombre", nomUser);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
                    Log.d(TAG, "La task es successful");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto Usuario
                        int id = document.getLong("Id").intValue();
                        String nombre = document.getString("Nombre");
                        String correo = document.getString("Correo");
                        String password = document.getString("Password");

                        Usuario usuario = new Usuario(id, nombre, correo, password);

                        Log.d(TAG, "Ya está creado el usuario : " + usuario);

                        listaUsuarios.add(usuario);
                    }
                    // Notificar al listener que el usuario ha sido obtenido exitosamente
                    listener.onUsuariosObtenidos(listaUsuarios);
                } else {
                    Log.d(TAG, "No encuentra registros");
                }

            }
        });
    }


    public static void deleteGrupo(String nomGrupo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereEqualTo("Nombre", nomGrupo);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtiene la referencia al documento del grupo y lo borra
                        DocumentReference docRef = gruposRef.document(document.getId());
                        docRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Grupo borrado correctamente");
                                } else {
                                    Log.d(TAG, "Error al borrar el grupo: ", task.getException());
                                }
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "Error obteniendo el grupo a borrar: ", task.getException());
                }
            }
        });
    }




}
