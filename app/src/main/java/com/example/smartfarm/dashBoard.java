package com.example.smartfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashBoard extends AppCompatActivity {

    private Button btnLedControl, btnArrosage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        // Références des éléments de l'interface utilisateur
        btnLedControl = findViewById(R.id.btnLedControl);
        btnArrosage = findViewById(R.id.btnArrosage);
        // Ajoutez un écouteur de clic au bouton pour LedControlActivity
        btnLedControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashBoard.this, LedControlActivity.class));
            }
        });

        // Ajoutez un écouteur de clic au bouton pour ArrosageActivity
        btnArrosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashBoard.this, Arrosage.class));
            }
        });
        DatabaseReference firebaseDatabase;

        TextView textViewTemperature = findViewById(R.id.textViewTemperature);
        TextView textViewPluie = findViewById(R.id.textViewPluie);
        TextView textViewHumidite = findViewById(R.id.textViewHumidite);
        TextView textViewLumiere = findViewById(R.id.textViewLumiere);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.child("temperature").getValue(String.class);
                String pluie = dataSnapshot.child("pluie").getValue(String.class);
                String humidite = dataSnapshot.child("humidite").getValue(String.class);
                String lumiere = dataSnapshot.child("lumiere").getValue(String.class);

                textViewTemperature.setText("Température:\n" + temperature);
                textViewPluie.setText("Pluie:\n" + pluie);
                textViewHumidite.setText("Humidité:\n" + humidite);
                textViewLumiere.setText("Lumière:\n" + lumiere);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });



    }
}