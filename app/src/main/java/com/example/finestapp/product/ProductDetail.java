package com.example.finestapp.product;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finestapp.MainActivity;
import com.example.finestapp.R;
import com.example.finestapp.Scancamera;
import com.example.finestapp.user.Login;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ProductDetail extends AppCompatActivity {

    private TextView productNameTextView;
    private TextView productPriceTextView;
    Button savebtn;
    EditText Name,price,datep,fourn2,margep;
    Button backbtn;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        // textbox product details pour edit btn

        Name = findViewById(R.id.name2);
        price = findViewById(R.id.editprice);
        datep = findViewById(R.id.editdate);
        margep = findViewById(R.id.editmarge);
        fourn2 = findViewById(R.id.fournisseur2);
        backbtn = findViewById(R.id.backbtn);
        savebtn = findViewById(R.id.savebtn);
        // Retrieve extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("productName")!=null && extras.getString("productMarge")!=null){
                String productName = extras.getString("productName");
                String productPrice = extras.getString("productPrice");
                String productDate = extras.getString("productDate");
                String productMarge = extras.getString("productMarge");
                String fournisseurName = extras.getString("productfourn");

                // Set values to TextView elements
                TextView textViewProductName = findViewById(R.id.textViewProductName);
                textViewProductName.setText("Product Name: " + productName);

                TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
                textViewProductPrice.setText("Product Price: " + productPrice + "DHS");

                TextView textViewProductDate = findViewById(R.id.textViewProductDate);
                textViewProductDate.setText("Product Date: " + productDate);

                TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
                textViewProductMarge.setText("Product Marge: " + productMarge + "DHS");

                TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);
                textViewFournisseurName.setText("Fournisseur Name: " + fournisseurName);
            }else if (extras.getString("FournisseurNom")!=null){
                TextView textViewFournName = findViewById(R.id.textViewProductName);
                textViewFournName.setText("Fournisseur Prenom : "+ extras.getString("FournisseurNom"));
                TextView textViewFournTel = findViewById(R.id.textViewProductPrice);
                textViewFournTel.setText("Telephone : "+extras.getString("FournisseurTelephone"));
                TextView textViewFournisseurPrenome = findViewById(R.id.textViewProductDate);
                textViewFournisseurPrenome.setText("Fournisseur Nom: " + extras.getString("FournisseurPrenom"));

            } else if (extras.getString("nomUser")!=null) {
                TextView textViewFournName = findViewById(R.id.textViewProductName);
                textViewFournName.setText("nom de utilisateur : "+ extras.getString("nomUser"));
                TextView textViewFournTel = findViewById(R.id.textViewProductPrice);
                textViewFournTel.setText("Prenom de utilisateur : "+extras.getString("prenomUser"));
                TextView textViewFournisseurPrenome = findViewById(R.id.textViewProductDate);
                textViewFournisseurPrenome.setText("Email de utilisateur : " + extras.getString("emailUser"));

            }
        }
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String productID = extras.getString("productId");
                if (!Name.equals("") && !fourn2.equals("") && !datep.equals("")
                && !price.equals("")
                ){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[6];
                            field[0] = "idProd";
                            field[1] = "NomProd";
                            field[2] = "PrixAchat";
                            field[3] = "dateProd";
                            field[4] = "MargeProd";
                            field[5] = "idFour";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = productID;
                            data[1] = String.valueOf(Name.getText());
                            data[2] = String.valueOf(price.getText());
                            data[3] = String.valueOf(datep.getText());
                            data[4] = String.valueOf(margep.getText());
                            data[5] = String.valueOf(fourn2.getText());
                            PutData putData = new PutData("https://ftapp.finesttechnology.ma/Loginregister/updateProd.php", "POST", field, data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String res = putData.getResult();
                                    if (res.equals("Updated Success")){
                                        startActivity(new Intent(getApplicationContext(),ProductList.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Update Success",Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.editbtn){
            Bundle extras = getIntent().getExtras();
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String productDate = extras.getString("productDate");
            String productMarge = extras.getString("productMarge");
            String fournisseurName = extras.getString("productfourn");
            backbtn = findViewById(R.id.backbtn);
            backbtn.setText("Cancel");
        Name.setVisibility(View.VISIBLE);
        Name.setText(productName);
        price.setVisibility(View.VISIBLE);
        price.setText(productPrice);
        datep.setVisibility(View.VISIBLE);
        datep.setText(productDate);
        margep.setVisibility(View.VISIBLE);
        margep.setText(productMarge);
        fourn2.setVisibility(View.VISIBLE);
        fourn2.setText(fournisseurName);
        TextView textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductName.setVisibility(View.GONE);
        TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
        textViewProductPrice.setVisibility(View.GONE);
        TextView textViewProductDate = findViewById(R.id.textViewProductDate);
        textViewProductDate.setVisibility(View.GONE);
        TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
        textViewProductMarge.setVisibility(View.GONE);
        TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);
        textViewFournisseurName.setVisibility(View.GONE);
        savebtn.setVisibility(View.VISIBLE);
        }
        else if (item.getItemId() == R.id.deletebtn) {
            alertDialog = new AlertDialog.Builder(ProductDetail.this);
            alertDialog.setTitle("Suppression");
            alertDialog.setMessage("Are you sure ?");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                            PutData putData = new PutData("https://ftapp.finesttechnology.ma/Loginregister/deleteProd.php", "POST", field, data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String res = putData.getResult();
                                    if (res.equals("Product deleted successfully.")){
                                        startActivity(new Intent(getApplicationContext(),ProductList.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Suppression with success", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    }
                    });

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Any Message for inform user cancel
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navdetail, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}