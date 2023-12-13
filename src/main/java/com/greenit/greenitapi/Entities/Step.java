package com.greenit.greenitapi.Entities;

public class Step {
    private int id;
    private Step previousStep;
    private String description;

    private String image;
    private String imagefield;

    public Step(String description, int id, Step previousStep, String image, String image64){
        this.previousStep = previousStep;
        this.description = description;
        this.id = id;
        this.image = image;
        this.imagefield = image64;
    }

    public Step(String description, int id, String image, String image64){
        this.previousStep = null;
        this.description = description;
        this.id = id;
        this.image = image;
        this.imagefield = image64;
    }
    public String getDescription() {
        return description;
    }

    public Step getPreviousStep() {
        return previousStep;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
    public String getImagefield() {
        return imagefield;
    }
}
