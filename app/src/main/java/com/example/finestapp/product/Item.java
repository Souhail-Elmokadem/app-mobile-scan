package com.example.finestapp.product;

public class Item {
    private String idprod;
    private String name;
    private String price;
    private String date;
    private String marge;
    private String fournisseurName;

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name, String price, String date, String marge, String fournisseurName){
        this.name = name;
        this.price = price;
        this.date = date;
        this.marge = marge;
        this.fournisseurName = fournisseurName;
    }

    public Item(String idprod, String name, String price, String date, String marge, String fournisseurName){
        this.idprod=idprod;
        this.name = name;
        this.price = price;
        this.date = date;
        this.marge = marge;
        this.fournisseurName = fournisseurName;
    }

    public String getId() { return this.idprod; }
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getMarge() {
        return marge;
    }

    public String getFournisseurName() {
        return fournisseurName;
    }
}


