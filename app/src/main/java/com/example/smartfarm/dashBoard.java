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
    FirebaseMessaging firebaseMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dashboard);

        //TextView textViewToken = findViewById(R.id.token);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        RequestQueue queue = Volley.newRequestQueue(this);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        System.out.println("Token fetched: " + token + " ..end");
                        //textViewToken.setText(token);
                        String cloudFunctionUrl = "YOUR_CLOUD_FUNCTION_ENDPOINT"; // Replace with your Cloud Function URL
                            String deviceToken = token;
                            String requestBody = "{ \"token\": \"" + deviceToken + "\" }";
                            // Create a request
                            StringRequest request = new StringRequest(Request.Method.POST, cloudFunctionUrl,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Handle successful response
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Handle error
                                        }
                                    }) {
                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    return requestBody.getBytes();
                                }

                                @Override
                                public String getBodyContentType() {
                                    return "application/json";
                                }
                            };

                                // Add the request to the RequestQueue
                               queue.add(request);
                    }
                });
        //get device token
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            String token = task.getResult();
//                            textViewToken.setText("Token:\n" + token);
////                            // Use the obtained token as needed
////
//                        } else {
//                            // Handle the error
//                        }
//                    }
//                });

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



//        FirebaseFirestore db = FirebaseFirestore.getInstance();



//        db.collection("data").document("LJR1TlDHAGFHlPsF2WSi")
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot document,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            // Handle errors
//                            return;
//                        }
//
//                        if (document != null && document.exists()) {
//                            Long temperature = document.getLong("temperature");
//                            Long pluie = document.getLong("pluie");
//                            Long humidite = document.getLong("humidite");
//                            Long lumiere = document.getLong("lumiere");
//
//                            // Update TextViews with Firestore data
//                            textViewTemperature.setText(temperature.toString());
//                            textViewPluie.setText(pluie.toString());
//                            textViewHumidite.setText(humidite.toString());
//                            textViewLumiere.setText(lumiere.toString());
//                        } else {
//                            // Handle the case where the document doesn't exist
//                        }
//                    }
//                });

        TextView textViewTemperature = findViewById(R.id.Temperature);
        TextView textViewPluie = findViewById(R.id.Pluie);
        TextView textViewHumidite = findViewById(R.id.Humidite);
        TextView textViewLumiere = findViewById(R.id.Lumiere);
        DatabaseReference firebaseDatabase;
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.child("temperature").getValue(String.class);
                String pluie = dataSnapshot.child("pluie").getValue(String.class);
                String humidite = dataSnapshot.child("humidite").getValue(String.class);
                String lumiere = dataSnapshot.child("lumiere").getValue(String.class);

                textViewTemperature.setText(temperature);
                textViewPluie.setText(pluie);
                textViewHumidite.setText(humidite);
                textViewLumiere.setText(lumiere);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });





    }


}