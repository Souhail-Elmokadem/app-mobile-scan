package com.example.finestapp.product;

public class Item {
    private String idprod;
    private String name;
    private String price;
    private String date;
    private String marge;
    private String fournisseurName;

    private String idFour;

    // for product iptv
    private String code;

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

    // constructor used four products physique
    public Item(String idprod, String name, String price, String date, String marge, String fournisseurName,String idfour){
        this.idprod = idprod;
        this.name = name;
        this.price = price;
        this.date = date;
        this.marge = marge;
        this.fournisseurName = fournisseurName;
        this.idFour=idfour;
    }
    // constructor used for iptv products
    public Item(String idprod,String name,String CodeProd){
        this.idprod=idprod;
        this.name=name;
        this.code=CodeProd;
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

    public String getCode() {
        return code;
    }

    public String getMarge() {
        return marge;
    }

    public String getFournisseurName() {
        return fournisseurName;
    }

    public String getIdFour(){return idFour;}
}

//pushing project