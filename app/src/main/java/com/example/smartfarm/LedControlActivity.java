package com.example.smartfarm;// LedControlActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LedControlActivity extends AppCompatActivity {

    private DatabaseReference ledStatusRef;
    private Switch switchToggleLed;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ledcontrol);

        switchToggleLed = findViewById(R.id.switchToggleLed);
        btnBack = findViewById(R.id.btnBack); // Initialisation du bouton de retour
        // Obtenez une référence à l'état de la LED dans Firebase
        ledStatusRef = FirebaseDatabase.getInstance().getReference().child("ledStatus");

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