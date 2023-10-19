package com.greenit.greenitapi.Entities;

public class Post {
    private User creator;
    private Step firstStep;
    private int id;
    private String serverName;

    public Post(User creator, Step firstStep, int id, String serverName){
        this.creator = creator;
        this.id = id;
        this.firstStep = firstStep;
        this.serverName = serverName;
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

    public int getId() {
        return id;
    }
}
