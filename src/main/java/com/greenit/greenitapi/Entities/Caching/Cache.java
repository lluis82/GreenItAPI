package com.greenit.greenitapi.Entities.Caching;

import java.util.HashMap;
import java.util.Iterator;

public class Cache {
    private static Cache instance = null;

    private static HashMap<Request, Response> cache = new HashMap<>(20);
    public static Cache getInstance(){
    if(instance == null) return new Cache(); else return instance;
    }

    private Cache(){}

    public static void purge(){
        System.out.println("CACHE PURGADA");
        cache.clear();
    }

    public static Response getFromCache(Request r){
        if(!cache.containsKey(r)) return null;
        System.out.println("CACHE HIT!!");
        return cache.get(r);
    }

    public static void addToCache(Request r, Response s){
        if(!cache.containsKey(r)) cache.put(r,s);
        else cache.replace(r,s);
    }

    public static void deleteFromCache(Request r){
        System.out.println("CACHE ACTUALIZADA");
        cache.remove(r);
    }

    public static void deleteIterableCustomFromCache(Request r){
        //este codigo cursed es por una excepcion de concurrencia, no preguntes...
        System.out.println("CACHE ACTUALIZADA CON ITERABLE v2");
        for(Iterator<Request> iterator = cache.keySet().iterator(); iterator.hasNext();){
            Request g = iterator.next();
            if(g.getBody().get(0).equals(r.getBody().get(0))) iterator.remove();
        }
    }
}
