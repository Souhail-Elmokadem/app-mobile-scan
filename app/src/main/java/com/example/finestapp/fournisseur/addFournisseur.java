package com.example.finestapp.fournisseur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finestapp.R;
import com.example.finestapp.fournisseur.FournisseurList;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class addFournisseur extends AppCompatActivity {
    private Button cancel,addbtn;
    EditText prenom,tel,nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fournisseur);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        cancel = findViewById(R.id.cancel);
        addbtn = findViewById(R.id.addbtn);
        nom = findViewById(R.id.name);
        prenom = findViewById(R.id.prenomtxt);
        tel = findViewById(R.id.teltxt);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nom,Prenom,Tel;
                Nom = String.valueOf(nom.getText());
                Prenom = String.valueOf(prenom.getText());
                Tel = String.valueOf(tel.getText());
                if(nom.getText().toString().trim().length()>0 && tel.getText().toString().trim().length()>0){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "NomFour";
                            field[1] = "PrenomFour";
                            field[2] = "TelFour";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = Nom;
                            data[1] = Prenom;
                            data[2] = Tel;

                            PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/AddSupply.php", "POST", field, data);

                            if(putData.startPut()){
                                if(putData.onComplete()){
                                    String res = putData.getResult();
                                    if(res.equals("Add Success")){
                                        Intent intent = new Intent(getApplicationContext(), FournisseurList.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Fournisseur Ajouté !",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Ajout échoué !",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Remplire tout les champs vides !",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}