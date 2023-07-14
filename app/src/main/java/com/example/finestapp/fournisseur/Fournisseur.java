package com.example.finestapp.fournisseur;

public class Fournisseur {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;




    public Fournisseur (String id, String nom, String prenom, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return nom + " " + prenom;
    }

}
