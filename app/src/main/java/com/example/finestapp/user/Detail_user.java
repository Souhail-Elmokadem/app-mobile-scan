package com.example.finestapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.finestapp.R;
import com.example.finestapp.Server;
import com.example.finestapp.product.ProductList;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Detail_user extends AppCompatActivity {
    TextView  textViewUserNom,textViewUserPrenom,textViewUserEmail,TextViewUserTel;
    EditText editNomUser, editPrenomUser, editEmailUser,editTelUser;
    Button savebtn,backbtn;
    AlertDialog.Builder alertdialogue;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navdetail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.editbtn){

            Bundle extras = getIntent().getExtras();
                String isUser = extras.getString("idUser");
                String UserNom = extras.getString("nomUser");
                String UserPrenom = extras.getString("prenomUser");
                String UserEmail = extras.getString("emailUser");
                String telUser = extras.getString("telUser");


            textViewUserEmail.setVisibility(View.GONE);
            textViewUserNom.setVisibility(View.GONE);
            textViewUserPrenom.setVisibility(View.GONE);
            TextViewUserTel.setVisibility(View.GONE);
            editNomUser = findViewById(R.id.editNomUser);
            editPrenomUser = findViewById(R.id.EditPrenomUser);
            editEmailUser = findViewById(R.id.EditEmailUser);
            editTelUser = findViewById(R.id.editTelUser);
            editTelUser.setVisibility(View.VISIBLE);
            editEmailUser .setVisibility(View.VISIBLE);
            editPrenomUser.setVisibility(View.VISIBLE);
            editNomUser.setVisibility(View.VISIBLE);
            editNomUser.setText(UserNom);
            editEmailUser.setText(UserEmail);
            editPrenomUser.setText(UserPrenom);
            editTelUser.setText(telUser);
          savebtn = findViewById(R.id.savebtn);
          savebtn.setVisibility(View.VISIBLE);
          savebtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  if (editNomUser.getText().toString().trim().length() > 0 && editPrenomUser.getText().toString().trim().length() > 0
                          && editTelUser.getText().toString().trim().length() > 0 && editEmailUser.getText().toString().trim().length() > 0
                  ) {
                      Handler handler = new Handler(Looper.getMainLooper());
                      handler.post(new Runnable() {
                          @Override
                          public void run() {
                              String[] field = new String[5];
                              field[0] = "idUser";
                              field[1] = "NomUser";
                              field[2] = "PrenomUser";
                              field[3] = "EmailUser";
                              field[4] = "TelUser";
                              //Creating array for data
                              String[] data = new String[5];
                              data[0] = isUser;
                              data[1] = String.valueOf(editNomUser.getText());
                              data[2] = String.valueOf(editPrenomUser.getText());
                              data[3] = String.valueOf(editEmailUser.getText());
                              data[4] = String.valueOf(editTelUser.getText());

                              PutData putData = new PutData(Server.Url + "/Loginregister/updateUser.php", "POST", field, data);
                              if (putData.startPut() && putData.onComplete()) {
                                  String res = putData.getResult();
                                  if (res.equals("Updated Success")) {
                                      startActivity(new Intent(getApplicationContext(), UserList.class));
                                      finish();
                                      Toast.makeText(getApplicationContext(), "Updated Success", Toast.LENGTH_SHORT).show();
                                  } else {
                                      Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                  }
                              }
                          }
                      });
                  } else {
                      Toast.makeText(getApplicationContext(), "Remplire tous les champs vide", Toast.LENGTH_SHORT).show();
                  }

              }
          });
        } else if (item.getItemId()==R.id.deletebtn) {

            alertdialogue = new AlertDialog.Builder(Detail_user.this);
            alertdialogue.setTitle("Suppression")
                    .setMessage("Arue you sure ?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            //dialog.dismiss();
                        }
                    });
            alertdialogue.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[1];
                            field[0] = "idUser";
                            String[] data = new String[1];
                            data[0] = getIntent().getExtras().getString("idUser");
                            PutData putData = new PutData(Server.Url+"/Loginregister/deleteUser.php", "POST", field, data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String res = putData.getResult();
                                    if (res.equals("User deleted successfully.")){
                                        startActivity(new Intent(getApplicationContext(), UserList.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Suppression success", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Suppression Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                    });
                }
            });
            AlertDialog d = alertdialogue.create();
            d.show();


        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);



        Button Backbtnuser;
        Backbtnuser = findViewById(R.id.backbtnUser);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String UserNom = extras.getString("nomUser");
            String UserPrenom = extras.getString("prenomUser");
            String UserEmail = extras.getString("emailUser");
            String telUser = extras.getString("telUser");

            textViewUserNom = findViewById(R.id.textViewUserNom);
            textViewUserNom.setText("First Name: " + UserNom);

            textViewUserPrenom = findViewById(R.id.textViewUserPrenom);
            textViewUserPrenom.setText("Last Name: " + UserPrenom );

            textViewUserEmail = findViewById(R.id.textViewUserEmail);
            textViewUserEmail.setText("Email: " + UserEmail);

            TextViewUserTel =findViewById(R.id.textViewUserTel);
            TextViewUserTel.setText("Telephone : "+ telUser);
        }
        Backbtnuser = findViewById(R.id.backbtnUser);

        Backbtnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
    }
}
