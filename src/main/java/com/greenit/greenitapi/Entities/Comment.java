package com.greenit.greenitapi.Entities;

public class Comment {
    private User creator;
    private String text;
    private int id;

    private Comment replyto;

    public Comment(User creator, int id, String text, Comment replyto){
        this.creator = creator;
        this.id=id;
        this.text=text;
        this.replyto=replyto;
    }

    public Comment getReplyto() {
        return replyto;
    }

    public void setReplyto(Comment replyto) {
        this.replyto = replyto;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
