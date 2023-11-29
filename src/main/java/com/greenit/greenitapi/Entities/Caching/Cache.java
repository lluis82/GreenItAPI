package com.greenit.greenitapi.Entities.Caching;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Iterator;

public class Cache {
    private static Cache instance = null;

    private static int misses = 0;
    private static int hits = 0;
    private static int updates = 0;
    private static int purges = 0;

    //CONSUMO DE MEMORIA AL CREAR LA CLASE
    private static final long heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();

    private static HashMap<Request, Response> cache = new HashMap<>(20);
    public static Cache getInstance(){
    if(instance == null) return new Cache(); else return instance;
    }

    private Cache(){}

    public static void purge(){
        purges++;
        System.out.println("CACHE PURGADA");
        cache.clear();
    }

    public static Response getFromCache(Request r){
        if(!cache.containsKey(r)){
            misses++;
            System.out.println("CACHE MISS EN " + r.getBody().toString());
            return null;}
        hits++;
        System.out.println("CACHE HIT!! EN " +  r.getBody().toString() + " " + cache.get(r).getBody());
        return cache.get(r);
    }

    public static void addToCache(Request r, Response s){
        if(!cache.containsKey(r)) cache.put(r,s);
        //else cache.replace(r,s);
        //fun fact, gracias a los test me he dado cuenta de que esta línea no es necesaria :)
    }

    public static void deleteFromCache(Request r){
        updates++;
        System.out.println("CACHE ACTUALIZADA");
        cache.remove(r);
    }

    public static void deleteIterableCustomFromCache(Request r){
        //este codigo cursed es por una excepcion de concurrencia, no preguntes...
        System.out.println("CACHE ACTUALIZADA CON ITERABLE v2");
        for(Iterator<Request> iterator = cache.keySet().iterator(); iterator.hasNext();){
            Request g = iterator.next();
            if(g.getBody().get(0).equals(r.getBody().get(0))){updates++; iterator.remove();}
        }
    }
    public static String stats(){
        return "-== ESTADISTICAS DE LA CACHE ==-\nPeticiones: " +
                (hits+misses+purges) +
                "\nHits: " +
                hits +
                "\nMisses: " +
                misses +
                "\nUpdates: " +
                updates +
                "\nVeces purgada: " +
                purges +
                "\n Uso de memoria en MB (aprox): " +
                ((ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() - heapMemoryUsage)/1024)/1024 +
                "\n % de hits: " +
                hits/(hits+misses+purges);
    }
}
