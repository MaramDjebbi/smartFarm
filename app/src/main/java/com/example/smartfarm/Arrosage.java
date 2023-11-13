package com.example.smartfarm;

// MainActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Arrosage extends AppCompatActivity {

    private DatabaseReference wateringStatusRef;
    private Button btnToggleWatering;
    private DatabaseReference robinetStatusRef;
    private Button btnOuvrirRobinet;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrosage);
        btnOuvrirRobinet = findViewById(R.id.btnOuvrirRobinet);
        btnToggleWatering = findViewById(R.id.btnToggleWatering);
        btnBack = findViewById(R.id.btnBack); // Initialisation du bouton de retour
        // Obtenez une référence à l'état de l' dans Firebase
        wateringStatusRef = FirebaseDatabase.getInstance().getReference().child("wateringStatus");


        // Obtenez une référence à l'état du robinet dans Firebase
        robinetStatusRef = FirebaseDatabase.getInstance().getReference().child("robinetStatus");
        // Ajoutez un écouteur de clic au bouton
        btnToggleWatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inversez l'état de l' dans Firebase
                toggleWateringStatus();
            }


        });
        // Ajoutez un écouteur de clic au bouton de retour
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Démarrer l'activité du tableau de bord (dashboard)
                Intent intent = new Intent(Arrosage.this, dashBoard.class);
                startActivity(intent);
                finish(); // Terminez cette activité
            }
        });
        // Ajoutez un écouteur de clic au bouton
        btnOuvrirRobinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inversez l'état du robinet dans Firebase
                toggleRobinetStatus();
            }
        });

    }

    private void toggleWateringStatus() {
        // Obtenez l'état actuel de l' dans Firebase
        wateringStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Inversez l'état (true -> false, false -> true)
                Boolean currentStatus = dataSnapshot.getValue(Boolean.class);
                if (currentStatus != null) {
                    boolean newStatus = !currentStatus;

                    // Mettez à jour l'état de l' dans Firebase
                    wateringStatusRef.setValue(newStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gestion des erreurs
            }
        });
    }

    private void toggleRobinetStatus() {
        // Obtenez l'état actuel du robinet dans Firebase
        robinetStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Inversez l'état (true -> false, false -> true)
                Boolean currentStatus = dataSnapshot.getValue(Boolean.class);
                if (currentStatus != null) {
                    boolean newStatus = !currentStatus;

                    // Mettez à jour l'état du robinet dans Firebase
                    robinetStatusRef.setValue(newStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gestion des erreurs
            }
        });
    }
}

