package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddProduct extends AppCompatActivity {

    private Button cancel,addbtn;
    EditText name,price,marge,fournisseur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        cancel = findViewById(R.id.cancel);
        addbtn = findViewById(R.id.addbtn);
        name = findViewById(R.id.name);
        price = findViewById(R.id.prenom);
        marge = findViewById(R.id.tel);
        fournisseur = findViewById(R.id.fournisseur);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name,Price,Marge,Fournisseur;
                Name = String.valueOf(name.getText());
                Price = String.valueOf(price.getText());
                Marge = String.valueOf(marge.getText());
                Fournisseur = String.valueOf(fournisseur.getText());
                if(!Name.equals("") && !Price.equals("") && !Marge.equals("") && !Fournisseur.equals("")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "NomProd";
                            field[1] = "PrixAchat";
                            field[2] = "MargeProd";
                            field[3] = "idFour";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = Name;
                            data[1] = Price;
                            data[2] = Marge;
                            data[3] = Fournisseur;
                            //Adresse IP Local
                            PutData putData = new PutData("http://192.168.11.63/Loginregister/addproduct.php", "POST", field, data);
//                            //Adresse IP Cloud
//                            PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/addProduct.php", "POST", field, data);


                            if(putData.startPut()){
                                if(putData.onComplete()){
                                    String res = putData.getResult();
                                    if(res.equals("Added Success")){
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Added avec succes",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Remplire tout les champs vide",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}