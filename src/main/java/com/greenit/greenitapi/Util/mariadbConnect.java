package com.greenit.greenitapi.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Random;

public class mariadbConnect {
    private static Config config = new Config();
    public static Connection mdbconn() {
        Connection connection = null;
        try {
            //buffering de conexiones, si tardas en crear la conexión cada hilo de forma aleatoria es menos
            //probable que abras 30 conexiones simultáneas, porque das tiempo entre espera y espera a que acaben
            //otros hilos
            //prob podria mejorarse con un int compartido de nº de conexiones abiertas pero meh
            Thread.sleep(new Random().nextInt(1,3)*200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(
                    config.getMdbURL(), config.getMdbUser(), config.getMdbPass()
            );
        } catch (Exception e) {
            System.out.println("Error al conectar con la BD de mariaDB " + e);
        }
        return connection;
    }
}
