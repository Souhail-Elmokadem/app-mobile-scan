package com.example.finestapp.fournisseur;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.R;
import com.example.finestapp.SessionActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class FournisseurDetail extends AppCompatActivity {

    AlertDialog.Builder alertdialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SessionActivity sessionActivity = new SessionActivity(FournisseurDetail.this);
        if (Integer.parseInt(sessionActivity.getIdrole())==2){
            getMenuInflater().inflate(R.menu.navdetail, menu);
        }else{

        }
        return super.onCreateOptionsMenu(menu);
    }
    EditText editFournName,editFournPrenom,editFournTel;
    Button savebtn,backbtn, cancelbtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.editbtn){

            editFournName = findViewById(R.id.EditNom);
            editFournPrenom = findViewById(R.id.EditPrenom);
            editFournTel = findViewById(R.id.EditTele);
            savebtn =findViewById(R.id.savebtn);
            cancelbtn = findViewById(R.id.cancelbtn);

            LinearLayout linear = findViewById(R.id.linear);
            linear.setVisibility(View.VISIBLE);
//
//
            TextView textViewFournName = findViewById(R.id.textViewFournisseurNom) ;
            textViewFournName.setVisibility(View.GONE);
            TextView textViewFournTel = findViewById(R.id.textViewFournisseurPrenom);
            textViewFournTel.setVisibility(View.GONE);
            TextView textViewFournisseurPrenome = findViewById(R.id.textViewFournisseurTele);
            textViewFournisseurPrenome.setVisibility(View.GONE);


            editFournName.setVisibility(View.VISIBLE);
            editFournPrenom.setVisibility(View.VISIBLE);
            editFournTel.setVisibility(View.VISIBLE);
            editFournTel.setText(getIntent().getExtras().getString("FournisseurTelephone"));
            editFournName.setText(getIntent().getExtras().getString("FournisseurNom"));
            editFournPrenom.setText(getIntent().getExtras().getString("FournisseurPrenom"));


            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linear.setVisibility(View.GONE);

                    TextView textViewFournName = findViewById(R.id.textViewFournisseurNom) ;
                    textViewFournName.setVisibility(View.VISIBLE);
                    TextView textViewFournTel = findViewById(R.id.textViewFournisseurPrenom);
                    textViewFournTel.setVisibility(View.VISIBLE);
                    TextView textViewFournisseurPrenome = findViewById(R.id.textViewFournisseurTele);
                    textViewFournisseurPrenome.setVisibility(View.VISIBLE);

                    }
                }
            );
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fournisseurid = getIntent().getExtras().getString("fournisseurid");
                    if (editFournName.getText().toString().trim().length()>0
                            && editFournTel.getText().toString().trim().length()>0){
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[4];
                                field[0] = "idFour";
                                field[1] = "NomFour";
                                field[2] = "PrenomFour";
                                field[3] = "TelFour";
                                //Creating array for data
                                String[] data = new String[4];
                                data[0] = fournisseurid;
                                data[1] = String.valueOf(editFournName.getText());
                                data[2] = String.valueOf(editFournPrenom.getText());
                                data[3] = String.valueOf(editFournTel.getText());
                                PutData putData = new PutData("https://ftapp.finesttechnology.ma/Loginregister/updateFour.php","POST",field,data);
                                if (putData.startPut()){
                                    if (putData.onComplete()){
                                        String res = putData.getResult();
                                        if (res.equals("Updated Success")){
                                            startActivity(new Intent(getApplicationContext(),FournisseurList.class));
                                            finish();
                                            Toast.makeText(getApplicationContext(),"Fournisseur Modifié !",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Modification échouée",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"Remplire tous les champs vides !",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (item.getItemId()==R.id.deletebtn) {
            alertdialog = new AlertDialog.Builder(FournisseurDetail.this);
            alertdialog.setTitle("Suppression")
                    .setMessage("Tous les produits de ce fournisseur seront supprimés")
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("Supprimer quand même", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[1];
                                    field[0] = "idFour";
                                    String[] data = new String[1];
                                    data[0] = getIntent().getExtras().getString("fournisseurid");

                                    PutData putData = new PutData("https://ftapp.finesttechnology.ma/Loginregister/deletefourn.php","POST",field,data);
                                    if (putData.startPut()){
                                        if (putData.onComplete()){
                                            String res = putData.getResult();
                                            if(res.equals("fourn deleted successfully.")){
                                                startActivity(new Intent(getApplicationContext(),FournisseurList.class));
                                                finish();
                                                Toast.makeText(getApplicationContext(),"Fournisseur Supprimé !",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getApplicationContext(),"Suppression échouée !",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });
            AlertDialog dialog = alertdialog.create();
            dialog.show();
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseur_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        backbtn = findViewById(R.id.backbtnFour);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        // view data
        Bundle extras = getIntent().getExtras();
        TextView textViewFournName = findViewById(R.id.textViewFournisseurNom);
        textViewFournName.setVisibility(View.VISIBLE);
        textViewFournName.setText("NOM : "+ extras.getString("FournisseurNom"));
        TextView textViewFournTel = findViewById(R.id.textViewFournisseurTele);
        textViewFournTel.setVisibility(View.VISIBLE);
        TextView textViewFournisseurPrenome = findViewById(R.id.textViewFournisseurPrenom);
        textViewFournisseurPrenome.setText("PRENOM: " + extras.getString("FournisseurPrenom"));
        textViewFournTel.setText("TELEPHONE : "+extras.getString("FournisseurTelephone"));
        textViewFournisseurPrenome.setVisibility(View.VISIBLE);


        // end view
//
    }
}