package com.example.conectamoviljhc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Contactos extends AppCompatActivity {
    Button BtnCrearC,BtnMostrar,BtnEditar;
    private FirebaseFirestore mfirestore;


    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        BtnCrearC = findViewById(R.id.btnAgregarC);
        BtnMostrar = findViewById(R.id.BtnMostrar);
        BtnEditar = findViewById(R.id.btnEditarC);

        mfirestore = FirebaseFirestore.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Contacto");

        BtnCrearC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contactos.this, CrearContacto.class));
            }
        });

        BtnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contactos.this, mostrarContacto.class));
            }
        });

        BtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contactos.this, EditarContacto.class));
            }
        });
    }
}