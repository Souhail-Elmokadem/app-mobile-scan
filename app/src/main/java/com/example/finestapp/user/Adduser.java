package com.example.finestapp.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Adduser extends AppCompatActivity {
    EditText Prenom, Nom, Username, Password, Telephone,password2;
    Button Register;
    ProgressBar progressBar;
    RadioGroup roleRadioGroup;


    private Button leavebtn;


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        Prenom = findViewById(R.id.prenomtxt);
        Nom = findViewById(R.id.nom);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        Telephone = findViewById(R.id.telephone);
        TextView oldpassword2  = findViewById(R.id.OldPassword2Alert);
        Register = findViewById(R.id.registerbtn);

        progressBar = findViewById(R.id.progress);

        roleRadioGroup = findViewById(R.id.radioGroupRoles);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView passwordalert = findViewById(R.id.OldPasswordAlert);
                String prenom, nom, username, password, telephone;
                prenom = String.valueOf(Prenom.getText());
                nom = String.valueOf(Nom.getText());
                username = String.valueOf(Username.getText());
                password = String.valueOf(Password.getText());
                telephone = String.valueOf(Telephone.getText());


                int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
                if (selectedRoleId == -1) {
                    Toast.makeText(getApplicationContext(), "Selectionner Un Role", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRoleRadioButton = findViewById(selectedRoleId);
                String roleId = selectedRoleRadioButton.getTag().toString();

                password2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        oldpassword2.setText("");
                        oldpassword2.setVisibility(View.GONE);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                });
                Password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                       passwordalert.setText("");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if (!isValidEmail(username)){
                    Toast.makeText(getApplicationContext(),"Le format de l'email ne correspond pas ",Toast.LENGTH_SHORT).show();
                }
                else if (!Password.getText().toString().equals(password2.getText().toString())){
                    TextView oldpassword2  = findViewById(R.id.OldPassword2Alert);
                    oldpassword2.setText("Mot de passe ne correspond pas");
                    oldpassword2.setVisibility(View.VISIBLE);
                }
                 else if (password.trim().length()<5 ){


                    passwordalert.setVisibility(View.VISIBLE);
                    passwordalert.setText("mot de passe doit contenir au minimum 5 caractères");
                }
                else if(!prenom.equals("") && !nom.equals("") && !username.equals("") && !password.equals("") && !telephone.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);

                        //Start ProgressBar first (Set visibility VISIBLE)
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[6];
                                field[0] = "NomUser";
                                field[1] = "PrenomUser";
                                field[2] = "EmailUser";
                                field[3] = "PasswordUser";
                                field[4] = "TelUser";
                                field[5] = "RoleId";

                                //Creating array for data
                                String[] data = new String[6];
                                data[0] = prenom;
                                data[1] = nom;
                                data[2] = username;
                                data[3] = password;
                                data[4] = telephone;
                                data[5] = roleId;


                                //The IP-Adress means that devices needs to be connected to the same WIFI network
                                PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/addUser.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        //End ProgressBar (Set visibility to GONE)
                                        if (result.equals("User Added")) {
                                            Intent intent = new Intent(getApplicationContext(), UserList.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(getApplicationContext(), "Utilisateur Ajouté !", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Ajout utilisateur échouée", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Remplire tout les champs vides !", Toast.LENGTH_SHORT).show();
                    }
                }
        });




        leavebtn = findViewById(R.id.leavebtn);

        leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}