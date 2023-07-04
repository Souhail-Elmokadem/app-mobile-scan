package com.example.finestapp;

import java.util.Date;

public class Product {
    private int idProd;
    private String nomProd;
    private double prixAchat;
    private String dateProd;
    private double margeProd;
    private int idFour;
    private int idUser;

    public Product(int idProd, String nomProd, int prixAchat, String dateProd, int margeProd, int idFour, int idUser) {
        this.idProd = idProd;
        this.nomProd = nomProd;
        this.prixAchat = prixAchat;
        this.dateProd = dateProd;
        this.margeProd = margeProd;
        this.idFour = idFour;
        this.idUser = idUser;
    }

    public int getIdProd() {
        return idProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public String getDateProd() {
        return dateProd;
    }

    public double getMargeProd() {
        return margeProd;
    }

    public int getIdFour() {
        return idFour;
    }

    public int getIdUser() {
        return idUser;
    }
}
