package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Server;
import com.greenit.greenitapi.Services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        return (List<Server>) server.orElse(null);
    }

    @GetMapping("/server")
    public Server getServer() {
        Optional<Server> server = serverService.getownServer();
        return (Server) server.orElse(null);
    }

}
