package com.example.proyectofinaldam;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.proyectofinaldam.Clases.Encuesta;
import com.example.proyectofinaldam.Clases.Evento;
import com.example.proyectofinaldam.Clases.Grupo;
import com.example.proyectofinaldam.Clases.Usuario;
import com.example.proyectofinaldam.Clases.Votaciones;
import com.example.proyectofinaldam.gestion_grupos.VerGrupo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
    //Funcion que actualiza la contraseña a la que el usuario indica en el usuario que el correo obtenga.
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

    //Funcion actualiza el campo grupos - id Grupo a usuarios.
    public static void actualizarUsuario(int idUsuario, int idGrupo, interfaces.OnUsuarioActualizadoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");

        Query query = usuariosRef.whereEqualTo("Id", idUsuario);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener la referencia al documento del usuario
                        DocumentReference usuarioDocRef = document.getReference();

                        // Actualizar el campo "grupos" agregando el valor de "idGrupo"
                        usuarioDocRef.update("grupos", FieldValue.arrayUnion(idGrupo))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Actualización exitosa del usuario
                                        Log.d(TAG, "Usuario actualizado: " + idUsuario);
                                        listener.onUsuarioActualizado();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error al actualizar el usuario
                                        Log.e(TAG, "Error al actualizar el usuario: " + idUsuario, e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


    // Actualizamos el usuario cuando eliminamos el grupo para que no aparezca en su lista de grupos
    public static void actualizarUsuarioElimGrupo(int idUsuario, int idGrupo, interfaces.OnUsuarioActualizadoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuariosRef = db.collection("Usuarios");

        Query query = usuariosRef.whereEqualTo("Id", idUsuario);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtenemos la referencia al documento del usuario
                        DocumentReference usuarioDocRef = document.getReference();

                        // Actualizamos el campo "grupos" eliminando el valor de "idGrupo"
                        usuarioDocRef.update("grupos", FieldValue.arrayRemove(idGrupo))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Usuario actualizado: " + idUsuario);
                                        listener.onUsuarioActualizado();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Error al actualizar el usuario: " + idUsuario, e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public static void borrarEventosDeGrupo(Integer idGrupo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crear una lista para almacenar las operaciones de eliminación
        List<WriteBatch> batches = new ArrayList<>();

        // Consultar los eventos asociados al grupo
        db.collection("Eventos")
                .whereEqualTo("fk_IdGrupo", idGrupo)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Crear una operación de eliminación por cada evento encontrado
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        WriteBatch batch = db.batch();
                        batch.delete(document.getReference());
                        batches.add(batch);
                    }

                    // Ejecutar las operaciones de eliminación en lote
                    for (WriteBatch batch : batches) {
                        batch.commit();
                    }
                })
                .addOnFailureListener(e -> {
                    // Manejar el error si no se pudo obtener la lista de eventos
                    Log.e(TAG, "Error al obtener la lista de eventos", e);
                });
    }

    public static void borrarEncuestasDeEvento(Integer idEvento) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consultar las encuestas asociadas al evento
        db.collection("Encuesta")
                .whereEqualTo("fk_IdEvento", idEvento)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Crear una lista de lotes de escritura
                    List<WriteBatch> batches = new ArrayList<>();
                    // Obtener todas las encuestas encontradas
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        // Crear un nuevo lote de escritura
                        WriteBatch batch = db.batch();
                        batch.delete(document.getReference());
                        batches.add(batch);
                    }
                    // Ejecutar las operaciones de eliminación en lote
                    for (WriteBatch batch : batches) {
                        batch.commit();
                    }
                })
                .addOnFailureListener(e -> {
                    // Manejar el error si no se pudieron obtener las encuestas
                    Log.e(TAG, "Error al obtener las encuestas", e);
                });
    }
    public static void borrarVotacionesDeEncuesta(Integer idEncuesta) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consultar las encuestas asociadas al evento
        db.collection("Votaciones")
                .whereEqualTo("fk_IdEncuesta", idEncuesta)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Crear una lista de lotes de escritura
                    List<WriteBatch> batches = new ArrayList<>();
                    // Obtener todas las encuestas encontradas
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        // Crear un nuevo lote de escritura
                        WriteBatch batch = db.batch();
                        batch.delete(document.getReference());
                        batches.add(batch);
                    }
                    // Ejecutar las operaciones de eliminación en lote
                    for (WriteBatch batch : batches) {
                        batch.commit();
                    }
                })
                .addOnFailureListener(e -> {
                    // Manejar el error si no se pudieron obtener las encuestas
                    Log.e(TAG, "Error al obtener las encuestas", e);
                });
    }


    /*
        Obtenemos los datos de grupo por miembro a través del id del usuario
     */
    public static void obtenerGruposPorMiembro(int idUsuario, interfaces.OnGruposObtenidosListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereArrayContains("Miembros", idUsuario);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Grupo> listaGrupos = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // obtiene los datos del documento y crea un objeto grupo
                        Integer idGrupo = document.getLong("Id").intValue();
                        String grupoNombre = document.getString("Nombre");
                        String grupoColor = document.getString("Color");
                        Grupo grupo = new Grupo(idGrupo, grupoNombre, grupoColor);
                        listaGrupos.add(grupo);
                    }
                    listener.onGruposObtenidos(listaGrupos);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


    /*
        Obtenemos los datos de grupo pasandole el id del grupo.
    */
    public static void obtenerGrupoPorId(int idGrupo, VerGrupo.OnGrupoObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereEqualTo("Id", idGrupo);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // obtiene los datos del documento y crea un objeto grupo
                        int idGrupo = document.getLong("Id").intValue();
                        String grupoNombre = document.getString("Nombre");
                        String grupoDesc = document.getString("Descripcion");
                        String grupoColor = document.getString("Color");
                        List<Long> grupoMiembros = (List<Long>) document.get("Miembros");

                        // Convertimos la lista de Long a lista de Integer
                        List<Integer> grupoMiembrosInteger = new ArrayList<>();
                        for (Long miembro : grupoMiembros) {
                            grupoMiembrosInteger.add(miembro.intValue());
                        }

                        Grupo grupo = new Grupo(grupoNombre, grupoDesc, grupoColor, grupoMiembrosInteger);
                        Log.d(TAG, "Los id de los miembros del grupo: " + grupoNombre + " son :" + grupoMiembrosInteger);

                        listener.onGrupoObtenido(grupo);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

        });
    }


    // Solo saca el nombre del grupo al pasarle el ID.
    public static void obtenerNombreGrupoPorId(int idGrupo, VerGrupo.OnGrupoObtenidoListener listener) {
        obtenerGrupoPorId(idGrupo, new VerGrupo.OnGrupoObtenidoListener() {
            @Override
            public void onGrupoObtenido(Grupo grupo) {
                // Extraer solo el nombre del grupo
                String nombreGrupo = grupo.getNombre();

                // Crear un nuevo objeto Grupo con el nombre obtenido
                Grupo grupoNombre = new Grupo(nombreGrupo, "", "", new ArrayList<>());

                // Notificar al listener con el nuevo objeto Grupo
                listener.onGrupoObtenido(grupoNombre);
            }
        });
    }

    // La función comprueba si el idadministrador que le enviamos es el administrador del grupo que
    // obtenemos a través del idGrupo
    public static void esAdminGrupo(int idUserAEliminar, int idGrupo, final interfaces.OnCheckFieldListener listener) {
        Log.d(TAG, "EL id del grupo: " + idGrupo);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereEqualTo("Id", idGrupo).whereEqualTo("Administrador", idUserAEliminar);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    Log.d(TAG, "EL USUARIO ES EL ADMIN!!");
                    listener.onFieldExists(true);
                } else {
                    Log.d(TAG, "El usuario no es admin.");
                    listener.onFieldExists(false);
                }
            } else {
                Log.e(TAG, "Error al obtener el grupo de la base de datos: " + task.getException());
                listener.onFieldExists(false);
            }
        });
    }

    /*
       Conversor de fechas para introducir en el calendario
    */

    public static com.prolificinteractive.materialcalendarview.CalendarDay convertirFecha(String fechaString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = format.parse(fechaString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            return CalendarDay.from(
                    calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Funcion para obtener el color del grupo
     */

    public static void obtenerColorGrupo(int idGrupo, interfaces.OnColorGrupoObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereEqualTo("Id", idGrupo);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtenemos el color del grupo del documento
                        String color = document.getString("Color");

                        // Notificamos al listener que se ha obtenido el color del grupo
                        listener.onColorGrupoObtenido(Color.parseColor(color));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    listener.onColorGrupoObtenidoError();
                }
            }
        });
    }
    /*
        Funcion para obtener el usuario pasandole el id.
    */

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
        AtomicInteger contador = new AtomicInteger(0);

        for (int idUsuario : listaIdsUsuarios) {
            Log.d(TAG, "El usuario que se trata es: " + idUsuario);
            obtenerUsuarioPorId(idUsuario, new VerGrupo.OnUsuarioObtenidoListener() {

                @Override
                public void onUsuarioObtenido(Usuario usuario) {
                    Log.d(TAG, "Llega bien el usuario? " + usuario.getId());

                    usuarios.add(usuario);
                    int cont = contador.incrementAndGet();

                    if (cont == totalUsuarios) {
                            Log.d(TAG, "Pasamos al Listview? " + usuarios);
                            listener.onUsuariosObtenidos(usuarios);
                        }
                    }

            });
        }
    }
    /*
        Funcion obtener usuarios por nombre pasandole el nombre usuario
     */

    public static void obtenerUsuariosPorNombre(String nomUser, VerGrupo.OnUsuariosObtenidosListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usuarioRef = db.collection("Usuarios");

        usuarioRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int id = document.getLong("Id").intValue();
                        String nombre = document.getString("Nombre");
                        String correo = document.getString("Correo");
                        String password = document.getString("Password");

                        Usuario usuario = new Usuario(id, nombre, correo, password);
                        listaUsuarios.add(usuario);
                    }

                    // Filtrar la lista de usuarios por nombre sin distinguir entre mayúsculas y minúsculas
                    ArrayList<Usuario> usuariosFiltrados = new ArrayList<>();
                    for (Usuario usuario : listaUsuarios) {
                        if (usuario.getNombre().toLowerCase().contains(nomUser.toLowerCase())) {
                            usuariosFiltrados.add(usuario);
                        }
                    }

                    listener.onUsuariosObtenidos(usuariosFiltrados);
                } else {
                    Log.d(TAG, "No se encontraron registros");
                }
            }
        });
    }

    /*
        Funcion borrar grupo
     */

    public static void deleteGrupo(Integer idGrupo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gruposRef = db.collection("Grupos");

        Query query = gruposRef.whereEqualTo("Id", idGrupo);

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

    /*
        Funcion para actualizar la tabla grupo.
     */

    public static void actualizarGrupo(int idGrupo, String nombre, String descripcion, String color, List<Integer> miembros) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference grupoRef = db.collection("Grupos");

        // Obtener la referencia al documento del grupo para actualizarlo
        Query query = grupoRef.whereEqualTo("Id", idGrupo);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Actualizar los campos del grupo
                    document.getReference().update("Nombre", nombre);
                    document.getReference().update("Descripcion", descripcion);
                    document.getReference().update("Color", color);
                    document.getReference().update("Miembros", miembros);
                }
            } else {
                Log.d(TAG, "No se encontró el grupo con el nombre " + nombre);
            }
        });
    }


/**
 * Funciones de Evento.
 */
    /**
     * Funciones de Crear evento. Tendra la funcion getIdnewEvento, crearEvento.
     */
    //Se utiliza al crear un evento y se le suma +1 al Ultimo id del evento.
    public static void getIdnewEvento(final interfaces.OnGetNextIdValueListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        eventosRef
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
                        listener.onGetNextIdValue(-1);
                    }
                });
    }



    /**
     * Funcion de crear Evento.
     */
    public static void crearEvento(int id, String nombre, String descripcion, String fecha, int tipo, int finalizado, int idGrupo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        Map<String, Object> evento = new HashMap<>();
        evento.put("Id", id);
        evento.put("Nombre", nombre);
        evento.put("Descripcion", descripcion);
        evento.put("Fecha", fecha);
        evento.put("Tipo", tipo);
        evento.put("Finalizado", finalizado);
        evento.put("fk_IdGrupo", idGrupo);

        eventosRef.document().set(evento)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Evento creado exitosamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error al crear el evento", e);
                    }
                });
    }

    /**
     * Funcion EventoLVDerecho. Se usa en el ListView de Fragment derecho. Recoge los datos del spinner y devuelve el ID del evento y el Nombre.
     */
    public static void EventoLVDerecho(int valorEvento, int idGrupo, int finalizado, interfaces.OnEventosObtenidosListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Construir la consulta
        Query consulta = db.collection("Eventos")
                .whereEqualTo("Tipo", valorEvento)
                .whereEqualTo("fk_IdGrupo", idGrupo)
                .whereEqualTo("Finalizado", finalizado);

        // Ejecutar la consulta
        consulta.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Evento> listaEventos = new ArrayList<>();
                    // Obtener el ID y nombre del evento
                    if (task.getResult() != null && !task.getResult().isEmpty()) {

                        for (DocumentSnapshot documento : task.getResult()) {
                            int idEvento = documento.getLong("Id").intValue();
                            String nombreEvento = documento.getString("Nombre");
                            Evento evento = new Evento(idEvento, nombreEvento);
                            listaEventos.add(evento);
                        }
                        // Pasar la lista de eventos al listener
                        listener.onEventosObtenidos(listaEventos);
                    } else {
                        // No se encontraron resultados
                        Log.d("ConsultaEventos" , "No hay eventos en el documento if2");
                        //listener.onEventosObtenidos(new ArrayList<>());
                    }
                } else {
                    Log.d("ConsultaEventos" , "Se ha hecho la query pero no hay documento if1");
                   // listener.onEventosObtenidos(new ArrayList<>());
                }
            }
        });
    }
    /**
     * Funcion obtenerEventosPorId. Se le pasa el id del evento y devuelve todos los datos que tenga.
     */
    public static void obtenerEventosPorId(int idEvento, interfaces.OnEventoObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        Query query = eventosRef.whereEqualTo("Id", idEvento);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto evento
                        String nombre = document.getString("Nombre");
                        String descripcion = document.getString("Descripcion");
                        Integer tipo = document.getLong("Tipo").intValue();
                        String fecha = document.getString("Fecha");
                        Integer Finalizado = document.getLong("Finalizado").intValue();
                        Integer fk_idGrupo = document.getLong("fk_IdGrupo").intValue();

                        Evento evento = new Evento(nombre, descripcion, tipo, fecha, Finalizado, fk_idGrupo);

                        // Llamar al método onEventoObtenido del listener y pasarle el evento obtenido
                        listener.onEventoObtenido(evento);

                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    public static void obtenerEventosPorIdGrupo(int idGrupo, interfaces.OnEventoObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        Query query = eventosRef.whereEqualTo("fk_IdGrupo", idGrupo);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto evento
                        Integer id = document.getLong("Id").intValue();
                        String nombre = document.getString("Nombre");
                        String descripcion = document.getString("Descripcion");
                        Integer tipo = document.getLong("Tipo").intValue();
                        String fecha = document.getString("Fecha");
                        Integer Finalizado = document.getLong("Finalizado").intValue();
                        Integer fk_idGrupo = document.getLong("fk_IdGrupo").intValue();

                        Evento evento = new Evento(id, nombre, descripcion, tipo, fecha, Finalizado, fk_idGrupo);

                        // Llamar al método onEventoObtenido del listener y pasarle el evento obtenido
                        listener.onEventoObtenido(evento);

                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    /**
     * Funcion obtenerEventosPorId. Se le pasa el id del evento y devuelve todos los datos que tenga.
     */
    public static void obtenerEventosPorIdConfirmacion(int idEvento, interfaces.OnEventoObtenidoListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        Query query = eventosRef.whereEqualTo("Id", idEvento);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto evento
                        String nombre = document.getString("Nombre");
                        String descripcion = document.getString("Descripcion");
                        Integer tipo = document.getLong("Tipo").intValue();
                        String fecha = document.getString("Fecha");
                        Integer finalizado = document.getLong("Finalizado").intValue();
                        Integer fk_idGrupo = document.getLong("fk_IdGrupo").intValue();
                        List<Long> siConfirmados = (List<Long>) document.get("siConfirmados"); //Para recuperar los enteros de siConfirmados.
                        List<Long> noConfirmados = (List<Long>) document.get("NoConfirmados");
                        // Convertir la lista de Long a lista de Integer
                        List<Integer> siConfirmadosInteger = new ArrayList<>();
                        if (siConfirmados != null) {
                            for (Long idUsuario : siConfirmados) {
                                siConfirmadosInteger.add(idUsuario.intValue());
                            }
                        }
                        List<Integer> noConfirmadosInteger = new ArrayList<>();
                        if (noConfirmados != null) {
                            for (Long idUsuario : noConfirmados) {
                                noConfirmadosInteger.add(idUsuario.intValue());
                            }
                        }

                        // String nombre, String descripcion, Integer tipo, String fecha, Integer fk_idGrupo, List<Integer> siConfirmados, List<Integer> noConfirmados) {
                        Evento evento = new Evento(nombre, descripcion, tipo, fecha,finalizado, fk_idGrupo, siConfirmadosInteger, noConfirmadosInteger);

                        // Llamar al método onEventoObtenido del listener y pasarle el evento obtenido
                        listener.onEventoObtenido(evento);

                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    /*
        Funcion para actualizar el campo confirmacion en evento
     */
    public static void actualizarCampoConfirmacion(int idEvento, List<Integer> siconfirmados, List<Integer> noConfirmados) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        eventosRef.whereEqualTo("Id", idEvento)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String eventId = document.getId();

                        eventosRef.document(eventId)
                                .update("siConfirmados", siconfirmados, "NoConfirmados", noConfirmados)
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("Se actualizó la confirmación del evento con ID: " + idEvento);
                                })
                                .addOnFailureListener(e -> {
                                    System.out.println("Error al actualizar la confirmación del evento: " + e.getMessage());
                                });
                    } else {
                        System.out.println("No se encontró ningún evento con el ID: " + idEvento);
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error al obtener el evento: " + e.getMessage());
                });
    }


    /**
     * Funcion que actualice la tabla Eventos Abiertos modifique la fecha y añada la matriz de si confirmados y no confirmados.
     */
    public static void actualizarEventoAbiertoFinal(Integer eventoId, String nuevaFecha) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        Query query = eventosRef.whereEqualTo("Id", eventoId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    DocumentSnapshot documento = querySnapshot.getDocuments().get(0);
                    DocumentReference eventoDocRef = documento.getReference();

                    // Actualizar el campo "Fecha" en el documento
                    eventoDocRef.update("Fecha", nuevaFecha)
                            .addOnSuccessListener(aVoid -> {
                                System.out.println("Se actualizó la fecha del evento con ID: " + eventoId);
                            })
                            .addOnFailureListener(e -> {
                                System.out.println("Error al actualizar la fecha del evento: " + e.getMessage());
                            });

                    // Crear los campos "siConfirmados" y "noConfirmados" si no existen
                    if (!documento.contains("siConfirmados")) {
                        eventoDocRef.update("siConfirmados", new ArrayList<Integer>())
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("Se creó el campo 'siConfirmados' en el evento con ID: " + eventoId);
                                })
                                .addOnFailureListener(e -> {
                                    System.out.println("Error al crear el campo 'siConfirmados': " + e.getMessage());
                                });
                    }

                    if (!documento.contains("NoConfirmados")) {
                        eventoDocRef.update("NoConfirmados", new ArrayList<Integer>())
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("Se creó el campo 'noConfirmados' en el evento con ID: " + eventoId);
                                })
                                .addOnFailureListener(e -> {
                                    System.out.println("Error al crear el campo 'noConfirmados': " + e.getMessage());
                                });
                    }
                } else {
                    System.out.println("No se encontró ningún evento con ID: " + eventoId);
                }
            } else {
                System.out.println("Error al obtener el evento con ID: " + eventoId + ", " + task.getException().getMessage());
            }
        });
    }
    /**
     * Funcion que actualiza el campo finalizado en la tabla Eventos. Se llama y se usa en confirmacion cuando la fecha es posterior a la actual.
     */
    public static void actualizarCampoFinalizado(int idEvento, int valorFinalizado) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        Query query = eventosRef.whereEqualTo("Id", idEvento);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String documentId = document.getId();
                    eventosRef.document(documentId).update("Finalizado", valorFinalizado)
                            .addOnSuccessListener(aVoid -> {
                                System.out.println("Se actualizó el campo Finalizado del evento con ID: " + idEvento);
                            })
                            .addOnFailureListener(e -> {
                                System.out.println("Error al actualizar el campo Finalizado del evento: " + e.getMessage());
                            });
                }
            } else {
                System.out.println("Error al obtener el evento con ID: " + idEvento);
            }
        });
    }




    /**
     * Funcion getIDnewEncuesta. Hacia un select de la coleccion y consulta en orden descendente todos los IDS y guarda en una variable el mas grande. Este se le suma +1.
     * @param listener
     */
    public static void getIdnewEncuesta(final interfaces.OnGetNextIdValueListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference encuestasRef = db.collection("Encuesta");

        encuestasRef
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
                            listener.onGetNextIdValue(1);
                        }
                    } else {
                        listener.onGetNextIdValue(-1);
                    }
                });
    }
    /**
     * Funcion de crear Evento.
     */
    public static void crearEncuesta(int id, String fechaFinal, String fechaLimite, String opcion1,  String opcion2, String opcion3, int fk_IdEvento, int finalizada) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference encuestaRef = db.collection("Encuesta");

        Map<String, Object> encuesta = new HashMap<>();
        encuesta.put("Id", id);
        encuesta.put("FechaFinal", fechaFinal);
        encuesta.put("FechaLimite", fechaLimite);
        encuesta.put("Opcion1", opcion1);
        encuesta.put("Opcion2", opcion2);
        encuesta.put("Opcion3", opcion3);
        encuesta.put("fk_IdEvento", fk_IdEvento);
        encuesta.put("Finalizada", finalizada);

        encuestaRef.document().set(encuesta)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Encuesta creada correctamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al crear la encuesta
                        Log.e(TAG, "Error al crear el Encuesta", e);
                    }
                });
    }

    /**
     *  Funcion obtenerEncuestaPorId. Le pasamos el id del evento y nos devuelve toda la informacion que tiene la encuesta.
     * @param idEvento
     * @param listener
     */
    public static void obtenerEncuestasPorId(int idEvento, interfaces.onEncuestaObtenidaListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference encuestasRef = db.collection("Encuesta");

        Query query = encuestasRef.whereEqualTo("fk_IdEvento", idEvento);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto evento
                        int id = document.getLong("Id").intValue();
                        String fechaFinal = document.getString("FechaFinal");
                        String fechaLimite = document.getString("FechaLimite");
                        String opcion1 = document.getString("Opcion1");
                        String opcion2 = document.getString("Opcion2");
                        String opcion3 = document.getString("Opcion3");
                        int fk_IdEvento = document.getLong("fk_IdEvento").intValue();
                        int finalizada = document.getLong("Finalizada").intValue();

                        Encuesta encuesta = new Encuesta(id, finalizada, fechaFinal, fechaLimite, opcion1, opcion2, opcion3, fk_IdEvento);

                        // Llamar al método onEventoObtenido del listener y pasarle la encuesta obtenida
                        listener.onEncuestaObtenida(encuesta);
                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    /**
     * Funcion actualizarFechaFinal en la tabla encuesta.
     */

    public static void actualizarFechaFinalEncuesta(Integer encuestaId, String nuevaFechaFinal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference encuestasRef = db.collection("Encuesta");

        Query query = encuestasRef.whereEqualTo("Id", encuestaId);

        // Obtener los documentos que coinciden con la consulta
        query.get()
                .addOnSuccessListener(querySnapshot -> {
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                    if (!documents.isEmpty()) {
                        DocumentSnapshot doc = documents.get(0);

                        // Actualizar el campo "fechaFinal" en el documento
                        DocumentReference docRef = encuestasRef.document(doc.getId());
                        docRef.update("FechaFinal", nuevaFechaFinal)
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("Se actualizó la fecha final de la encuesta con ID: " + encuestaId);
                                })
                                .addOnFailureListener(e -> {
                                    System.out.println("Error al actualizar la fecha final de la encuesta: " + e.getMessage());
                                });
                    } else {
                        System.out.println("No se encontró ninguna encuesta con el ID: " + encuestaId);
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error al realizar la consulta: " + e.getMessage());
                });
    }


    /**
     * Funcion getIdNewVotacion comprueba en la coleccion Votacion el ultimo ID y le asigna +1 al nuevo.
     */
    public static void getIdnewVotacion(final interfaces.OnGetNextIdValueListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference encuestaRef = db.collection("Votaciones");

        encuestaRef
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
     * Funcion crear Votacion.
     */

    public static void crearVotaciones(int id, int opcion1,  int opcion2, int opcion3, List<Integer> usuariosVotados, int fk_idEncuesta) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference votacionesRef = db.collection("Votaciones");

        Map<String, Object> encuesta = new HashMap<>();
        encuesta.put("Id", id);
        encuesta.put("Recuento1", opcion1);
        encuesta.put("Recuento2", opcion2);
        encuesta.put("Recuento3", opcion3);
        encuesta.put("UsuarioVotado", usuariosVotados);
        encuesta.put("fk_IdEncuesta", fk_idEncuesta);


        votacionesRef.document().set(encuesta)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // La encuesta se creó correctamente
                        Log.d(TAG, "Encuesta creado exitosamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al crear la encuesta
                        Log.e(TAG, "Error al crear el Encusta", e);
                    }
                });
    }
    /**
     * Funcion getIdVotacion. le pasamos el IdEncuesta y nos devolvera la informacion de la votacion.
     */
    public static void obtenerVotacionPorId(int fk_idEncuesta, interfaces.onVotacionesObtenidaListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference votacionesRef = db.collection("Votaciones");

        Query query = votacionesRef.whereEqualTo("fk_IdEncuesta", fk_idEncuesta);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Obtener los datos del documento y crear un objeto evento
                        Integer id = document.getLong("Id").intValue();
                        Integer recuento1 = document.getLong("Recuento1").intValue();
                        Integer recuento2 = document.getLong("Recuento2").intValue();
                        Integer recuento3 = document.getLong("Recuento3").intValue();
                        List<Long> usuariosvotados = (List<Long>) document.get("UsuarioVotado");

                        // Convertir la lista de Long a lista de Integer
                        List<Integer> usuariovotadosInteger = new ArrayList<>();
                        for (Long idUsuario : usuariosvotados) {
                            usuariovotadosInteger.add(idUsuario.intValue());
                        }

                        Votaciones votaciones = new Votaciones(id, recuento1, recuento2, recuento3 , usuariovotadosInteger, fk_idEncuesta);

                        // Llamar al método onEventoObtenido del listener y pasarle el evento obtenido
                        listener.onVotacionesObtenida(votaciones);

                        break;
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }



    /**
     * Actualizar votacion. le pasamos la fk_idEncuesta y actualiza los datos que hay.
     */
    public static void actualizarVotacion(int fkIdEncuesta, int voto1, int voto2, int voto3, List<Integer> usuariosVotados) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference votacionesRef = db.collection("Votaciones");

        Query query = votacionesRef.whereEqualTo("fk_IdEncuesta", fkIdEncuesta);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String votacionId = document.getId();

                        // Actualizar los campos de la votación
                        int recuento1 = document.getLong("Recuento1").intValue() + voto1;
                        int recuento2 = document.getLong("Recuento2").intValue() + voto2;
                        int recuento3 = document.getLong("Recuento3").intValue() + voto3;
                        votacionesRef.document(votacionId).update(
                                        "Recuento1", recuento1,
                                        "Recuento2", recuento2,
                                        "Recuento3", recuento3,
                                        "UsuarioVotado", usuariosVotados
                                )
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Votación actualizada correctamente");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Error al actualizar la votación", e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    /**
     * Funcion para notificar al usuario.
     */
    public static void mostrarNotificacion(Context context, int idUsuario, String titulo, String mensaje) {
        // Identificador único para la notificación
        int idNotificacion = idUsuario;

        // Crear un canal de notificación
        String channelId = "CalendarLinkia";
        String channelNombre = "Canal de Notificaciones";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelNombre, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.foto_logo)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Mostrar la notificación
        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager.class);
        }
        notificationManager.notify(idNotificacion, builder.build());
    }





    /*public static void mostrarEventosCalendario(MaterialCalendarView materialCalendarView){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventosRef = db.collection("Eventos");

        eventosRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<CalendarDay> eventDates = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Date fechaEvento = document.getDate("fecha");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaEvento);
                        CalendarDay calendarDay = CalendarDay.from(calendar);

                        eventDates.add(calendarDay);
                    }

                    EventDecorator eventDecorator = new EventDecorator(Color.RED, eventDates);
                    materialCalendarView.addDecorators(eventDecorator);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }*/

}//Final de laa conexionBBDD

