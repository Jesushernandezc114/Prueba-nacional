package com.example.conectamoviljhc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearContacto extends AppCompatActivity {
    Button BtnCrear,BtnVolver;
    EditText Name,NumT,Email;
    public FirebaseFirestore mfirestore;

    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);
        BtnCrear = findViewById(R.id.btnEditar);
        BtnVolver = findViewById(R.id.btnVolver);
        Name = findViewById(R.id.txtNombre);
        NumT = findViewById(R.id.txtNumT);
        Email = findViewById(R.id.txtCorreo);

        mfirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Contacto");

        BtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = Name.getText().toString().trim();
                String numerotel = NumT.getText().toString().trim();
                String gmail = Email.getText().toString().trim();


                if(nombre.isEmpty() && numerotel.isEmpty() && gmail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    postContac(nombre, numerotel, gmail);
                }
            }
        });
    }

    private void postContac(String nombre, String numerotel, String gmail) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", nombre);
        map.put("numerotel", numerotel);
        map.put("Email", gmail);

        mfirestore.collection("Contacto").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
