package com.greenit.greenitapi;

import com.greenit.greenitapi.Util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.util.Scanner;

@SpringBootApplication
public class GreenItApiApplication {
    private static Config config = new Config();
    public static void main(String[] args) {
        SpringApplication.run(GreenItApiApplication.class, args);
        System.out.println("GreenIt API Server " + config.getSrvName());
        if(config.inDebug()){System.out.println("=-= RUNNING IN DEBUG MODE =-=");}else{System.out.println("-=- RUNNING IN AWS MODE -=-");}
        System.out.println("path to resources: " + Config.getResourcesLocation());

        //ASCII Art
        Scanner input;
        try {
            input = new Scanner(new File(Config.getResourcesLocation() + "art"));
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el ASCII art para el nombre del server " + e);
        }
        System.out.println("Corriendo sobre: " + System.getProperty("os.name"));
    }
}
