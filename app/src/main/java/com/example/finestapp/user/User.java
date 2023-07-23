package com.example.finestapp.user;

public class User {
    private  String idUser;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    private String idrole;


    public User (String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public User (String idUser,String nom, String prenom,String email,String telephone,String idrole) {
        this.idUser=idUser;
        this.nom = nom;
        this.prenom = prenom;
        this.email=email;
        this.telephone=telephone;
        this.idrole=idrole;
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
    public  String getrole(){
        return this.idrole;
    }
    public String getroleName(){
        if (Integer.parseInt(getrole())==2){
            return "Admin";
        }else{
            return "visiteur";
        }
    }

    public String getTelephone() {
        return telephone;
    }
}
