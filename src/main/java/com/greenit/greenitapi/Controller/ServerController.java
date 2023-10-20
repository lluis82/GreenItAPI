package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Server;
import com.greenit.greenitapi.Services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Server> getServers() {
        Optional<List<Server>> server = serverService.getServers();
        if(server == null) return null;
        return (List<Server>) server.orElse(null);
    }

    @GetMapping("/meet")
    public String meetServer(@RequestParam String serverip, @RequestParam String servername) {
        String sol = serverService.srvRegister(serverip, servername);
        return sol;
    }

    @GetMapping("/server")
    public Server getServer() {
        Optional<Server> server = serverService.getownServer();
        if(server == null) return null;
        return (Server) server.orElse(null);
    }

}
