package com.greenit.greenitapi;

import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.enigma;
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


        //ASCII Art
        Scanner input;
        try {
            input = new Scanner(new File(Config.getResourcesLocation() + "art"));
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el ASCII art para el nombre del server");
        }

        //Numeros secretos de ip
        System.out.println("Números secretos para el servidor " + config.getSrvName());
        System.out.println(enigma.encode(config.getSrvName(), config.getSrvIp()));
        System.out.println(enigma.decode("Touka",enigma.encode(config.getSrvName(), config.getSrvIp())));
        System.out.println("Corriendo sobre: " + System.getProperty("os.name"));
    }
}
