package com.example.conectamoviljhc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.conectamoviljhc.Model.Contacto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class mostrarContacto extends AppCompatActivity {
    public ListView Lista;
    public List<Contacto> listaContactos;
    public ArrayAdapter<Contacto> adapter;

    private FirebaseFirestore mfirestore;
    public DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_contacto);
        mfirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Contacto");
        listaContactos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContactos);

        // Configura el ListView
        Lista = findViewById(R.id.Lista);
        Lista.setAdapter(adapter);

        // Configura el listener para obtener y mostrar los contactos existentes
        obtenerYMostrarContactos();
    }

    private void obtenerYMostrarContactos() {

        mfirestore.collection("Contacto")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Contacto contacto = documentSnapshot.toObject(Contacto.class);

                            listaContactos.add(contacto);
                            Log.d("MostrarContactos", "Contacto: " + "Nombre: " + contacto.getName() +
                                    ", Email: " + contacto.getEmail() +
                                    ", Número Telefónico: " + contacto.getNumerotel());

                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MostrarContactos", "Error al obtener contactos", e);
                        Toast.makeText(getApplicationContext(), "Error al obtener contactos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}