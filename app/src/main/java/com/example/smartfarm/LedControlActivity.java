package com.example.smartfarm;// LedControlActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LedControlActivity extends AppCompatActivity {

    private DatabaseReference ledStatusRef;
    private Switch switchToggleLed;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ledcontrol);

        switchToggleLed = findViewById(R.id.switchToggleLed);
        btnBack = findViewById(R.id.Back);
        TextView textViewLumiere = findViewById(R.id.lumiere);
        TextView textViewLumiereVrai = findViewById(R.id.LumiereVrai);
        TextView textViewLumiereFaux = findViewById(R.id.LumiereFaux);
        TextView textViewBulbOn = findViewById(R.id.bulbon);
        TextView textViewBulbOff = findViewById(R.id.bulboff);
        TextView textViewLumiereEnCours = findViewById(R.id.LumiereEnCours);
        // Obtenez une référence à l'état de la LED dans Firebase
        ledStatusRef = FirebaseDatabase.getInstance().getReference().child("ledStatus");


        DatabaseReference firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Lumiere = dataSnapshot.child("lumiere").getValue(String.class);
                Boolean ledStatus = dataSnapshot.child("ledStatus").getValue(Boolean.class);
                textViewLumiere.setText(Lumiere);
                if(ledStatus){
                    textViewLumiereVrai.setVisibility(View.GONE);
                    textViewLumiereFaux.setVisibility(View.GONE);
                    textViewBulbOff.setVisibility(View.GONE);
                    textViewBulbOn.setVisibility(View.GONE);
                    textViewLumiereEnCours.setVisibility(View.VISIBLE);
                }
                else if (Integer.parseInt(Lumiere) < 70) {
                    textViewLumiereVrai.setVisibility(View.VISIBLE);
                    textViewLumiereFaux.setVisibility(View.GONE);
                    textViewBulbOff.setVisibility(View.VISIBLE);
                    textViewBulbOn.setVisibility(View.GONE);
                    textViewLumiereEnCours.setVisibility(View.GONE);
                } else {
                    textViewBulbOn.setVisibility(View.VISIBLE);
                    textViewLumiereFaux.setVisibility(View.VISIBLE);
                    textViewBulbOff.setVisibility(View.GONE);
                    textViewLumiereVrai.setVisibility(View.GONE);
                    textViewLumiereEnCours.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


        //display the switch according the its value in the data base (on for true and off for false)
        ledStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean toggleStatus = dataSnapshot.getValue(Boolean.class);
                if (toggleStatus != null) {
                    switchToggleLed.setChecked(toggleStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });




        // Ajoutez un écouteur de changement d'état au Switch
        switchToggleLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Appelé lorsque l'état du Switch change
                toggleLedStatus(isChecked);
            }
        });


        // Ajoutez un écouteur de clic au bouton de retour
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Démarrer l'activité du tableau de bord (dashboard)
                Intent intent = new Intent(LedControlActivity.this, dashBoard.class);
                startActivity(intent);
                finish(); // Terminez cette activité
            }
        });
    }







    private void toggleLedStatus(boolean newStatus) {
        // Mettez à jour l'état de la LED dans Firebase avec la nouvelle valeur (true/false)
        ledStatusRef.setValue(newStatus);
    }
}