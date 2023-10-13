package com.greenit.greenitapi.Entities;

public class Server {
    private String name;
    private String ip;
    private Boolean isSeed=false;//si es el server principal, la "semilla", puede que no lo necesitemos

    public Server(String name, String ip, Boolean isSeed){
        this.name=name;
        this.ip=ip;
        this.isSeed=isSeed;
    }

    public String getName() {
        return name;
    }

    public Boolean isSeed() {
        return isSeed;
    }

    public String getIp() {
        return ip;
    }
}
