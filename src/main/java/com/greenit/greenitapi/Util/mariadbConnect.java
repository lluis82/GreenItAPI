package com.greenit.greenitapi.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class mariadbConnect {
    private static Config config = new Config();
    public static Connection mdbconn() {
        Connection connection = null;
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
