package com.example.finestapp.user;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.R;
import com.example.finestapp.Server;
import com.example.finestapp.SessionActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class UserDetail extends AppCompatActivity {
    TextView  textViewUserNom,textViewUserPrenom,textViewUserEmail,TextViewUserTel;
    EditText editNomUser, editPrenomUser, editEmailUser,editTelUser;
    Button savebtn,backbtn, cancelbtn;
    AlertDialog.Builder alertdialogue;
    RadioGroup radioGroupRoles; // Added RadioGroup variable
    RadioButton radioAdmin, radioVisitor; // Added RadioButton variables

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SessionActivity sessionActivity = new SessionActivity(UserDetail.this);
        if (Integer.parseInt(sessionActivity.getIdrole())==2){
            getMenuInflater().inflate(R.menu.navdetail, menu);
        }else{

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.editbtn){
            LinearLayout  layout = findViewById(R.id.barproduct);
            layout.setVisibility(View.GONE);
            Bundle extras = getIntent().getExtras();
            String isUser = extras.getString("idUser");
            String UserNom = extras.getString("nomUser");
            String UserPrenom = extras.getString("prenomUser");
            String UserEmail = extras.getString("emailUser");
            String telUser = extras.getString("telUser");
            String roleId = extras.getString("idrole");


            LinearLayout linear = findViewById(R.id.linear);

            editNomUser = findViewById(R.id.editNomUser);
            editPrenomUser = findViewById(R.id.EditPrenomUser);
            editEmailUser = findViewById(R.id.EditEmailUser);
            editTelUser = findViewById(R.id.editTelUser);

            textViewUserEmail.setVisibility(View.GONE);
            textViewUserNom.setVisibility(View.GONE);

            TextViewUserTel.setVisibility(View.GONE);

            editNomUser.setText(UserNom);
            editEmailUser.setText(UserEmail);
            editPrenomUser.setText(UserPrenom);
            editTelUser.setText(telUser);
            RadioButton rAdmin = findViewById(R.id.radioAdmin);
            RadioButton rVisiteur = findViewById(R.id.radioVisitor);
            if (Integer.parseInt(roleId)==2){
                rAdmin.setChecked(true);
            }else{
                rVisiteur.setChecked(true);
            }

            linear.setVisibility(View.VISIBLE);

            savebtn = findViewById(R.id.savebtn);
            cancelbtn = findViewById(R.id.cancelbtn);
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout = findViewById(R.id.linear);
                    linearLayout.setVisibility(View.GONE);
                    LinearLayout  layout = findViewById(R.id.barproduct);
                    layout.setVisibility(View.VISIBLE);
                    TextView textViewUserNom = findViewById(R.id.textViewUserNom);

                    TextView textViewUserEmail = findViewById(R.id.textViewUserEmail);
                    TextView textViewUserTel = findViewById(R.id.textViewUserTel);

                    textViewUserNom.setVisibility(View.VISIBLE);
                    
                    textViewUserEmail.setVisibility(View.VISIBLE);
                    textViewUserTel.setVisibility(View.VISIBLE);

                }
            });

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
                                String[] field = new String[6]; // Increase the array size to accommodate roleId
                                field[0] = "idUser";
                                field[1] = "NomUser";
                                field[2] = "PrenomUser";
                                field[3] = "EmailUser";
                                field[4] = "TelUser";
                                field[5] = "RoleId";

                                //Creating array for data
                                String[] data = new String[6]; // Increase the array size to accommodate roleId
                                data[0] = isUser;
                                data[1] = String.valueOf(editNomUser.getText());
                                data[2] = String.valueOf(editPrenomUser.getText());
                                data[3] = String.valueOf(editEmailUser.getText());
                                data[4] = String.valueOf(editTelUser.getText());
                                data[5] = getSelectedRoleId(); // Get the selected role ID


                                PutData putData = new PutData(Server.Url + "/Loginregister/updateUser.php", "POST", field, data);
                                if (putData.startPut() && putData.onComplete()) {
                                    String res = putData.getResult();
                                    if (res.equals("Updated Success")) {
                                        UserList.userlist.finish();
                                        startActivity(new Intent(getApplicationContext(), UserList.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Utilisateur Modifié !", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Modification échouée", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Remplire tous les champs vides !", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else if (item.getItemId()==R.id.deletebtn) {

            alertdialogue = new AlertDialog.Builder(UserDetail.this);
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
                                        UserList.userlist.finish();
                                        startActivity(new Intent(getApplicationContext(), UserList.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Utilisateur Supprimé !", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Suppression échouée", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                    });
                }
            });
            AlertDialog d = alertdialogue.create();
            d.show();


        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayout linearLayout = findViewById(R.id.linear);
        linearLayout.setVisibility(View.GONE);
        //remove shadow under actionbar
        getSupportActionBar().setElevation(0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String UserNom = extras.getString("nomUser");
            String UserPrenom = extras.getString("prenomUser");
            String UserEmail = extras.getString("emailUser");
            String telUser = extras.getString("telUser");
            String roleId = extras.getString("roleId"); // Add this line to retrieve the roleId

            textViewUserNom = findViewById(R.id.textViewUserNom);
            textViewUserNom.setText( UserNom +" "+UserPrenom);



            textViewUserEmail = findViewById(R.id.textViewUserEmail);
            textViewUserEmail.setText("Email: " + UserEmail);

            TextViewUserTel =findViewById(R.id.textViewUserTel);
            TextViewUserTel.setText("Telephone : "+ telUser);
        }


        radioGroupRoles = findViewById(R.id.radioGroupRoles);
        radioAdmin = findViewById(R.id.radioAdmin);
        radioVisitor = findViewById(R.id.radioVisitor);
    }
    // Helper method to get the selected role ID from the RadioGroup
    private String getSelectedRoleId() {
        int selectedRadioButtonId = radioGroupRoles.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.radioAdmin) {
            return "2"; // Return the ID of the Admin role
        } else if (selectedRadioButtonId == R.id.radioVisitor) {
            return "3"; // Return the ID of the Visitor role
        } else {
            return ""; // No role selected
        }
    }
}