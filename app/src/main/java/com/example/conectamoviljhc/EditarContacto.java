package com.example.conectamoviljhc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conectamoviljhc.Model.Contacto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarContacto extends AppCompatActivity {

    Button Btnbuscar, BtnEliminar, BtnEditar;
    private EditText editTextNombre;
    private EditText editTextNumero;
    private EditText editTextEmail;

    public FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contacto);

        editTextNombre = findViewById(R.id.txtNombre);
        editTextNumero = findViewById(R.id.txtNumT);
        editTextEmail = findViewById(R.id.txtCorreo);
        Btnbuscar = findViewById(R.id.Btnbuscar);
        BtnEditar = findViewById(R.id.btnEditar);
        BtnEliminar = findViewById(R.id.btnEliminar);
        mfirestore = FirebaseFirestore.getInstance();
        Contacto contacto = (Contacto) getIntent().getSerializableExtra("Contacto");

            Btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarContacto();
            }
        });
        BtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarContacto();
            }
        });
        BtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarContacto();
            }
        });
    }
    private void buscarContacto() {
       final String nombreBuscado = editTextNombre.getText().toString().trim();

        if (!nombreBuscado.isEmpty()) {
            mfirestore.collection("Contacto")
                    .whereEqualTo("name", nombreBuscado)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {

                                    Contacto contacto = document.toObject(Contacto.class);

                                    // Muestra la información en las TextViews
                                    editTextEmail.setText(contacto.getEmail());
                                    editTextNumero.setText(contacto.getNumerotel());
                                    return;
                                }
                            } else {
                                editTextEmail.setText("");
                                editTextNumero.setText("");
                            }
                        }
                    });




            }}
        private void eliminarContacto() {
            final String nombreBuscado = editTextNombre.getText().toString().trim();

            if (!nombreBuscado.isEmpty()) {
                mfirestore.collection("Contacto")
                        .whereEqualTo("name", nombreBuscado)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                        // Obtén la referencia al documento y elimínalo
                                        DocumentReference documentReference = mfirestore.collection("Contacto").document(document.getId());
                                        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> deleteTask) {
                                                if (deleteTask.isSuccessful()) {
                                                    Toast.makeText(EditarContacto.this, "Contacto eliminado exitosamente", Toast.LENGTH_SHORT).show();
                                                    // Limpiar las vistas o realizar otras acciones después de eliminar
                                                    editTextNombre.setText("");
                                                    editTextEmail.setText("");
                                                    editTextNumero.setText("");
                                                } else {
                                                    Toast.makeText(EditarContacto.this, "Error al eliminar contacto", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        return; // Si hay múltiples coincidencias, toma solo la primera
                                    }
                                } else {
                                    // No se encontró el contacto
                                    Toast.makeText(EditarContacto.this, "No se encontró el contacto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }}
                private void editarContacto() {
                    String nombreBuscado = editTextNombre.getText().toString().trim();
                    String nuevoCorreo = editTextEmail.getText().toString().trim();
                    String nuevoNumTelefonico = editTextNumero.getText().toString().trim();

                    if (!nombreBuscado.isEmpty()) {
                        mfirestore.collection("Contacto")
                                .whereEqualTo("name", nombreBuscado)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful() && task.getResult() != null) {
                                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                                // Obtén la referencia al documento
                                                DocumentReference documentReference = mfirestore.collection("Contacto").document(document.getId());

                                                // Crea un mapa con los nuevos datos
                                                Map<String, Object> nuevosDatos = new HashMap<>();
                                                nuevosDatos.put("Email", nuevoCorreo);
                                                nuevosDatos.put("numerotel", nuevoNumTelefonico);

                                                // Actualiza el documento con los nuevos datos
                                                documentReference.update(nuevosDatos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> updateTask) {
                                                        if (updateTask.isSuccessful()) {
                                                            Toast.makeText(EditarContacto.this, "Contacto editado exitosamente", Toast.LENGTH_SHORT).show();
                                                            // Limpiar las vistas o realizar otras acciones después de editar
                                                            editTextNombre.setText("");
                                                            editTextEmail.setText("");
                                                            editTextNumero.setText("");
                                                        } else {
                                                            Toast.makeText(EditarContacto.this, "Error al editar contacto", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                                return; // Si hay múltiples coincidencias, toma solo la primera
                                            }
                                        } else {
                                            // No se encontró el contacto
                                            Toast.makeText(EditarContacto.this, "No se encontró el contacto", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }