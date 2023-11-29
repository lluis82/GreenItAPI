package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Entities.Server;
import com.greenit.greenitapi.Services.ServerService;
import com.greenit.greenitapi.Util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/servers")
    public static List<Server> getServers() {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/servers")));
        if(cached != null) return (List<Server>) cached.getBody();
        List<Server> server;
        try{
        server = ServerService.getServers().orElse(null);}catch(Exception e){return new ArrayList<>();}
        Cache.addToCache(new Request().setBody(List.of("/servers")), new Response().setBody(server));
        if(server == null) return new ArrayList<>();
        return server;
    }

    @GetMapping("/meet")
    public static String meetServer(@RequestParam String serverip, @RequestParam String servername) {
        String sol = ServerService.srvRegister(serverip, servername);
        if(sol.contains("OK")) Cache.deleteFromCache(new Request().setBody(List.of("/servers")));
        return sol;
    }

    @GetMapping("/server")
    public static Server getServer() {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/server")));
        if(cached != null) return (Server) cached.getBody().get(0);
        Server server;
        try{
        server = ServerService.getownServer().orElse(null);}catch(Exception e){return null;}
        Cache.addToCache(new Request().setBody(List.of("/server")), new Response().setBody(List.of(server)));
        if(server == null) return null;
        return server;
    }

    @GetMapping("/purgecache")
    public static String purge(){
        Cache.purge();
        Config c = new Config();
        return c.getSrvName() + " OK";
    }

    @GetMapping("/statscache")
    public static String stats(){
        return Cache.stats();
    }

}
