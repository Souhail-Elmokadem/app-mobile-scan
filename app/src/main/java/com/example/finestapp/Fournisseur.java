package com.example.finestapp;

public class Fournisseur {
    private String nom;
    private String prenom;
    private String telephone;


    public Fournisseur (String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }
}
