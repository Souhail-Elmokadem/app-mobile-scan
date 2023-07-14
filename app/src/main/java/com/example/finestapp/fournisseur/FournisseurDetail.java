package com.example.finestapp.fournisseur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finestapp.R;

public class FournisseurDetail extends AppCompatActivity {

    EditText Nom,Prenom,Tele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseur_detail);

        Nom=findViewById(R.id.EditNom);
        Prenom=findViewById(R.id.EditPrenom);
        Tele=findViewById(R.id.EditTele);

        Button Backbtn;

        // Retrieve extras
        Bundle extras = getIntent().getExtras();
        if (extras !=null) {
            String SupplyNom = extras.getString("FournisseurNom");
            String SupplyPrenom = extras.getString("FournisseurPrenom");
            String SupplyTele = extras.getString("FournisseurTelephone");

            // Set values to TextView elements
            TextView textViewFournisseurNom = findViewById(R.id.textViewFournisseurNom);
            textViewFournisseurNom.setText("First Name: " + SupplyNom);

            TextView textViewFournisseurPrenom = findViewById(R.id.textViewFournisseurPrenom);
            textViewFournisseurPrenom.setText("Last Price: " + SupplyPrenom );

            TextView textViewFournisseurTele = findViewById(R.id.textViewFournisseurTele);
            textViewFournisseurTele.setText("Phone: " + SupplyTele);
        }

        Backbtn = findViewById(R.id.backbtnFour);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}