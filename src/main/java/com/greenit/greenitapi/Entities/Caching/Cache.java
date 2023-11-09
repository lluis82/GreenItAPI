package com.greenit.greenitapi.Entities.Caching;

import java.util.HashMap;

public class Cache {
    private static Cache instance = null;

    private static HashMap<Request, Response> cache = new HashMap<>(20);
    public static Cache getInstance(){
    if(instance == null) return new Cache(); else return instance;
    }

    private Cache(){}

    public static void purge(){cache.clear();}

    public static Response getFromCache(Request r){
        if(!cache.containsKey(r)) return null;
        return cache.get(r);
    }

    public static void addToCache(Request r, Response s){
        if(!cache.containsKey(r)) cache.put(r,s);
        else cache.replace(r,s);
    }

    public static void deleteFromCache(Request r){
        cache.remove(r);
    }
}
