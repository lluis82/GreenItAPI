package com.greenit.greenitapi;

import com.greenit.greenitapi.Util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootApplication
public class GreenItApiApplication {
    private static Config config = new Config();

    public static void main(String[] args) {
        SpringApplication.run(GreenItApiApplication.class, args);
        System.out.println("Server API corriendo");
        System.out.println(config.getSrvName());
   }
}
