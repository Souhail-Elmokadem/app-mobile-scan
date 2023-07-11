package com.example.finestapp.user;

public class User {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;


    public User (String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public User (String nom, String prenom,String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email=email;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
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
