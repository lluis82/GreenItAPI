package com.greenit.greenitapi.Entities;

public class User {
    private String displayName;
    private String email;
    private String password;
    private String serverName;//el server donde reside la info del usuario
    private String description;
    private String image;

    public User(String displayName, String email, String password, String serverName, String image, String description){
        this.displayName=displayName;
        this.email=email;
        this.password=password;
        this.serverName=serverName;
        this.image= image;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getServerName() {
        return serverName;
    }
    public String getImage(){return image;}
    public String getDescription(){return description;}
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
