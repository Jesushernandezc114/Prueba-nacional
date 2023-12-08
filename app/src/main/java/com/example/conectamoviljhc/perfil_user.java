package com.example.conectamoviljhc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class perfil_user extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button BtnSfoto, BtnGuardar, Btnmodify;
    ImageView Foto;
    TextView txtNombre;
    TextView txtCorreo;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);
        BtnGuardar = findViewById(R.id.btnGuardarCa);
        BtnSfoto = findViewById(R.id.btnSF);
        Btnmodify = findViewById(R.id.btnModificar);
        txtCorreo = findViewById(R.id.TXTCorreo);
        txtNombre = findViewById(R.id.TXTNombre);
        Foto = findViewById(R.id.Foto);


        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            loadUserData(user);
        }

        BtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        BtnSfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la galería para seleccionar una imagen
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Btnmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onModifyUserClick(view);
            }
        });
    }


    public void onModifyUserClick(View view) {
        showModifyUserDialog();
    }

    private void showModifyUserDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modificar Usuario");
        builder.setMessage("Añade aquí la lógica para modificar el usuario");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void loadUserData(FirebaseUser user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener la información del usuario desde la base de datos
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String correo = dataSnapshot.child("correo").getValue(String.class);

                    // Actualizar la interfaz de usuario con la información del usuario
                    if (nombre != null) {
                        txtNombre.setText(nombre);
                    }

                    if (correo != null) {
                        txtCorreo.setText(correo);
                    }

                    // Cargar la imagen de perfil (si está disponible)
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(Foto);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores si es necesario
            }
        });
    }

    private void saveChanges() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String newUsername = txtNombre.getText().toString().trim();
            String newEmail = txtCorreo.getText().toString().trim();

            if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newEmail)) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar información del usuario en la base de datos
            updateUserData(user.getUid(), newUsername, newEmail);
        }
    }

    private void updateUserData(String userId, String newUsername, String newEmail) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userId);

        // Actualizar nombre y correo electrónico
        databaseReference.child("name").setValue(newUsername);
        databaseReference.child("Email").setValue(newEmail);

        // Mostrar mensaje de éxito
        Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
    }

    // Añade este método para manejar el resultado de la selección de la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtén la URI de la imagen seleccionada
            Uri imageUri = data.getData();

            // Sube la imagen al Firebase Storage y actualiza la URL en la base de datos
            uploadImageAndUpdateProfile(imageUri);
        }
    }

    private void uploadImageAndUpdateProfile(Uri imageUri) {
        if (firebaseAuth.getCurrentUser() != null) {
            String userId = firebaseAuth.getCurrentUser().getUid();

            // Referencia al Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile_images").child(userId);

            // Sube la imagen al Firebase Storage
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Obtiene la URL de la imagen después de subirla con éxito
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Actualiza la URL de la imagen en la base de datos
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userId);
                            databaseReference.child("imageUrl").setValue(uri.toString());

                            // Carga la imagen en el ImageView (opcional)
                            Picasso.get().load(uri).into(Foto);

                            // Muestra un mensaje de éxito
                            Toast.makeText(this, "Foto de perfil cambiada con éxito", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Maneja errores en caso de que falle la subida de la imagen
                        Toast.makeText(this, "Error al subir la foto de perfil", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
