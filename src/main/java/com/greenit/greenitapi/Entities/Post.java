package com.greenit.greenitapi.Entities;

public class Post {
    private User creator;
    private Step firstStep;
    private int id;
    private String serverName;
    private String image;
    private String description;
    private String imagefield;
    private String title;

    public Post(User creator, Step firstStep, int id, String serverName, String image, String description, String image64, String title){
        this.creator = creator;
        this.id = id;
        this.firstStep = firstStep;
        this.serverName = serverName;
        this.image = image;
        this.description = description;
        this.imagefield = image64;
        this.title = title;
    }

    public Step getFirstStep() {
        return this.firstStep;
    }
    public User getCreator() {
        return creator;
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
    public String getTitle(){return title;}
}
