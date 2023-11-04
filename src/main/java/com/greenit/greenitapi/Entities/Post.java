package com.greenit.greenitapi.Entities;

public class Post {
    private User creator;
    private Step firstStep;
    private int id;
    private String serverName;
    private String image;
    private String description;
    private String imagefield;

    public Post(User creator, Step firstStep, int id, String serverName, String image, String description, String image64){
        this.creator = creator;
        this.id = id;
        this.firstStep = firstStep;
        this.serverName = serverName;
        this.image = image;
        this.description = description;
        this.imagefield = image64;
    }

    public Step getFirstStep() {
        return this.firstStep;
    }
    public User getCreator() {
        return creator;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFirstStep(Step firstStep) {
        this.firstStep = firstStep;
    }
    public void setCreator(User creator) {
        this.creator = creator;
    }
    public String getServerName() {
        return serverName;
    }
    public String getImage() {
        return image;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public String getImagefield() {
        return imagefield;
    }
}
