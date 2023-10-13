package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Server;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    private static Connection connection;
    private static Config config = new Config();

    public static Optional<List<Server>> getServers(){
        Optional<List<Server>> optional;
        List<Server> sol = new ArrayList<>();
        Server server = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM servers s
                """)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String ip = resultSet.getString("ip");
                Boolean isSeed = resultSet.getBoolean("isSeed");
                server = new Server(name, ip, isSeed);
                sol.add(server);
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        return optional;

    }

    public static Optional<Server> getownServer(){
        Optional<Server> optional;
        Server server = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM servers s
                    WHERE name like ?
                """)) {
            statement.setString(1,config.getSrvName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String ip = resultSet.getString("ip");
                Boolean isSeed = resultSet.getBoolean("isSeed");
                server = new Server(name, ip, isSeed);
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(server);
        return optional;

    }


}
