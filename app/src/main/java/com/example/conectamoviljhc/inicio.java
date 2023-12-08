package com.example.conectamoviljhc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class inicio extends AppCompatActivity {
    Button Btnexit;
    Button BtnContact;
    Button BtnEditPerfil, BTnChat;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
         mAuth = FirebaseAuth.getInstance();
        Btnexit = findViewById(R.id.btnCerrar);
        BtnContact = findViewById(R.id.BtnContactos);
        BtnEditPerfil = findViewById(R.id.BtnPerfil);
        BTnChat = findViewById(R.id.btnChat);
        Btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                finish();
                startActivity(new Intent(inicio.this, MainActivity.class));
            }
        });

        BtnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inicio.this, Contactos.class));
            }
        });

        BtnEditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(inicio.this, perfil_user.class));

            }
        });
        BTnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(inicio.this, Chat.class));

            }
        });

    }


}