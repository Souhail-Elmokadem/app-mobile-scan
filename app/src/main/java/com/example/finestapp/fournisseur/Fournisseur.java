package com.example.finestapp.fournisseur;

public class Fournisseur {
    private  String fournisseurid;
    private String nom;
    private String prenom;
    private String telephone;


    public Fournisseur (String fournisseurid,String nom, String prenom, String telephone) {
        this.fournisseurid=fournisseurid;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public String getFournisseurid() {
        return fournisseurid;
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
