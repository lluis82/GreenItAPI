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

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagefield() {
        return imagefield;
    }

    public void setImagefield(String imagefield) {
        this.imagefield = imagefield;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
