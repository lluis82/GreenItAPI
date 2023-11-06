package com.greenit.greenitapi.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class mariadbConnect {
    private static Config config = new Config();
    private static int counter = 0;
    public static Connection mdbconn() {
        Connection connection = null;
        while(counter > 5 && config.bufferIncomingConnections()){
            System.out.println("ESTOY ESPERANDO, "  + Thread.currentThread().getName() + ", " + counter);
            try {
                Thread.sleep((long)(Math.random() * 4000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        counter++;
        System.out.println("HE ENTRADO WOOOOOOO, "  + Thread.currentThread().getName() + ", " + counter);
        try {
            connection = DriverManager.getConnection(
                    config.getMdbURL(), config.getMdbUser(), config.getMdbPass()
            );
        } catch (Exception e) {
            System.out.println("Error al conectar con la BD de mariaDB " + e);
        }
        counter--;
        System.out.println("HE ACABADO, "  + Thread.currentThread().getName() + ", " + counter);
        return connection;
    }
}
