package com.example.finestapp.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.finestapp.Server;
import com.example.finestapp.SessionActivity;
import com.example.finestapp.product.frag_products.fragment_ProductMain;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ProductDetailDigital extends AppCompatActivity {
    Button savebtn, cancelbtn, backbtn;
    EditText name, code;
    TextView textViewName, textViewCode;
    AlertDialog.Builder alertDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SessionActivity sessionActivity = new SessionActivity(ProductDetailDigital.this);
        if (Integer.parseInt(sessionActivity.getIdrole())==2){
            getMenuInflater().inflate(R.menu.navdetail, menu);
        }else{

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_digital);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        //remove shadow under actionbar
        getSupportActionBar().setElevation(0);
        name = findViewById(R.id.editname);
        code = findViewById(R.id.editcode);

        textViewName = findViewById(R.id.textViewProductName);
        textViewCode = findViewById(R.id.textViewProductCode);


        savebtn = findViewById(R.id.savebtn);
        cancelbtn = findViewById(R.id.cancelbtn);



        Bundle extras = getIntent().getExtras();
        textViewName.setVisibility(View.VISIBLE);
        textViewName.setText(extras.getString("productName"));

        textViewCode.setVisibility(View.VISIBLE);
        textViewCode.setText("Code : "+extras.getString("productCode"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.editbtn) {

           LinearLayout layout = findViewById(R.id.barproduct);
           layout.setVisibility(View.GONE);
            Bundle extras = getIntent().getExtras();
            String productName = extras.getString("productName");
            String productCode = extras.getString("productCode");

            name.setText(productName);
            code.setText(productCode);

            LinearLayout linear = findViewById(R.id.linear);
            linear.setVisibility(View.VISIBLE);

            textViewName.setVisibility(View.GONE);
            textViewCode.setVisibility(View.GONE);

            cancelbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    linear.setVisibility(View.GONE);
                    LinearLayout layout = findViewById(R.id.barproduct);
                    layout.setVisibility(View.VISIBLE);
                    textViewName.setVisibility(View.VISIBLE);
                    textViewCode.setVisibility(View.VISIBLE);

                }
            });

            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();
                    String productID = extras.getString("productId");

                    if (name.getText().toString().trim().length() > 0 && code.getText().toString().trim().length() > 0) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[3];
                                field[0] = "idProd";
                                field[1] = "NomProd";
                                field[2] = "CodeProd";

                                String[] data = new String[3];
                                data[0] = productID;
                                data[1] = String.valueOf(name.getText());
                                data[2] = String.valueOf(code.getText());

                                PutData putData = new PutData(Server.Url + "/Loginregister/updateProduitDigital.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String res = putData.getResult();
                                        if (res.equals("Updated Success")) {
                                            fragment_ProductMain.fa.finish();
                                            startActivity(new Intent(getApplicationContext(), fragment_ProductMain.class));
                                            finish();
                                            Toast.makeText(getApplicationContext(), "Produit Digital Modifié !", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Modification échouée", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Remplire tout les champs vides !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (item.getItemId() == R.id.deletebtn) {
            alertDialog = new AlertDialog.Builder(ProductDetailDigital.this);
            alertDialog.setTitle("Suppression");
            alertDialog.setMessage("Êtes-vous sûr de vouloir supprimer ce produit ?");
            alertDialog.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[1];
                            field[0] = "idProd";
                            String[] data = new String[1];
                            data[0] = getIntent().getExtras().getString("productId");
                            PutData putData = new PutData(Server.Url + "/Loginregister/deleteProdDigital.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String res = putData.getResult();
                                    if (res.equals("Product deleted successfully.")) {
                                        fragment_ProductMain.fa.finish();
                                        startActivity(new Intent(getApplicationContext(), fragment_ProductMain.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Produit Digital Supprimé !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
            });
            alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Any Message for inform user cancel
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


