package com.greenit.greenitapi;

import com.greenit.greenitapi.Controller.ServerController;
import com.greenit.greenitapi.Entities.Server;
import com.greenit.greenitapi.Services.ServerService;
import com.greenit.greenitapi.Util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class GreenItApiApplication {
    private static Config config = new Config();
    public static void main(String[] args) {
        SpringApplication.run(GreenItApiApplication.class, args);
        System.out.println("GreenIt API Server " + config.getSrvName());

        ServerService serverService = new ServerService();
        ServerController serverController = new ServerController(serverService);
        List<Server> a = serverController.getServers();
        System.out.println(a.toString());


        //ASCII Art
        try {
            Scanner input = new Scanner(new File("src/main/resources/art"));
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el ASCII art para el nombre del server");
        }
    }
}
