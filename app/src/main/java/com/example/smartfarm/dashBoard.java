package com.example.smartfarm;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.*;


public class dashBoard extends AppCompatActivity {

    private Button btnLedControl, btnArrosage;
//    FirebaseMessaging firebaseMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard);
        //start the service
//        Intent serviceIntent = new Intent(this, myService.class);
//        startService(serviceIntent);


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


        TextView textViewTemperature = findViewById(R.id.Temperature);
        TextView textViewPluie = findViewById(R.id.Pluie);
        TextView textViewHumidite = findViewById(R.id.Humidite);
        TextView textViewLumiere = findViewById(R.id.Lumiere);
        TextView textViewGaz = findViewById(R.id.Gaz);
        TextView textViewMotion = findViewById(R.id.Motion);

        DatabaseReference firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String temperature = dataSnapshot.child("sensorData/temperature").getValue(String.class);
                    String humidite = dataSnapshot.child("sensorData/humidity").getValue(String.class);
                    String pluie = dataSnapshot.child("moistureData/pluie").getValue(String.class);
                    String lumiere = dataSnapshot.child("lightData/lightValue").getValue(String.class);
                    String gaz = dataSnapshot.child("gasData/gasValue").getValue(String.class);
                    Boolean motion = dataSnapshot.child("motionData/motionDetected").getValue(Boolean.class);


                    if (temperature != null && humidite != null && pluie != null && lumiere != null && gaz != null) {
                        textViewTemperature.setText(temperature);
                        textViewPluie.setText(pluie);
                        textViewHumidite.setText(humidite);
                        textViewLumiere.setText(lumiere);
                        textViewGaz.setText(gaz);
                        if(motion)
                            textViewMotion.setText("Mouvement detecté !");
                        else
                            textViewMotion.setText("Pas de mouvement !");
                    } else {
                        // Handle null values if needed
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Log any exceptions for debugging
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }


}