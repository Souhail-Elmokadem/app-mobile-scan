package com.example.finestapp.user;

public class User {
    private  String idUser;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;


    public User (String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public User (String idUser,String nom, String prenom,String email,String telephone) {
        this.idUser=idUser;
        this.nom = nom;
        this.prenom = prenom;
        this.email=email;
        this.telephone=telephone;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }
}
