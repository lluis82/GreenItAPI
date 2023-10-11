package com.greenit.greenitapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootApplication
public class GreenItApiApplication {

    private static Connection connection;

    public static void main(String[] args) {
        SpringApplication.run(GreenItApiApplication.class, args);
        System.out.println("Server API corriendo");

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/merequetengue",
                    "root", "adre1234"
            );
            if(connection.isClosed()!= false){System.out.println("CONECTADO CON MARIADB");}
        }catch(Exception e){
            System.out.println("Error al conectar con la BD de mariaDB");
        }

        try (PreparedStatement statement = connection.prepareStatement("""
            SELECT *
            FROM Users
        """)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1); // by column index
                String name = resultSet.getString("userName"); // by column name
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                System.out.println("--== DATOS ==--");
                System.out.println("ID:" + id);
                System.out.println("Nombre:" + name);
                System.out.println("Email:" + email);
                System.out.println("Password:" + password);
            }
        }catch(Exception e){
            System.out.println("Error al recuperar info de la BD");
        }



    }
}
