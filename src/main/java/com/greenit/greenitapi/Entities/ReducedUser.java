package com.greenit.greenitapi.Entities;

public class ReducedUser {

    private int id;
    private String displayName;
    private String image;
    private String imagefield;

    public ReducedUser(int id, String displayName, String image, String imagefield) {
        this.id = id;
        this.displayName = displayName;
        this.image = image;
        this.imagefield = imagefield;
    }

    public String getDisplayName() {
        return displayName;
    }


    public String getImage() {
        return image;
    }


    public String getImagefield() {
        return imagefield;
    }


    public int getId() {
        return id;
    }

}
