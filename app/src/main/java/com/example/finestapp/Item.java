package com.example.finestapp;

public class Item {
    private String name;
    private String price;
    private String date;
    private int marge;
    private String fournisseurName;

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name, String price, String date, int marge, String fournisseurName){
        this.name = name;
        this.price = price;
        this.date = date;
        this.marge = marge;
        this.fournisseurName = fournisseurName;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getMarge() {
        return marge;
    }

    public String getFournisseurName() {
        return fournisseurName;
    }
}


