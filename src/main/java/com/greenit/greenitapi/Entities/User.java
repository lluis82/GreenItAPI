package com.greenit.greenitapi.Entities;

public class User {
    private int id;
    private String displayName;
    private String email;
    private String password;
    private String serverName;//el server donde reside la info del usuario
    private String description;
    private String image;
    private String imagefield;

    public User(int id, String displayName, String email, String password, String serverName, String image, String description, String image64){
        this.id =id;
        this.displayName=displayName;
        this.email=email;
        this.password=password;
        this.serverName=serverName;
        this.image= image;
        this.description = description;
        this.imagefield = image64;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getServerName() {
        return serverName;
    }
    public String getImage(){return image;}
    public String getImagefield(){return imagefield;}
    public String getDescription(){return description;}
    public int getId() {
        return id;
    }
}
