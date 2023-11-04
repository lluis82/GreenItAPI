package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Entities.User;
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
public class LikeService {
    private static Connection connection;
    private static Config config = new Config();

    public static String like(String username, int postid) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO likes (user, post) VALUES ((SELECT u.id from users u where u.username like ?), ?) 
                """)) {
            statement.setInt(2,postid);
            statement.setString(1,username);
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
        }
        return config.getSrvName() + " OK";
    }

    public static String unlike(String username, int postid) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM likes WHERE user = (SELECT u.id from users u where u.username like ?) AND POST = ?
                """)) {
            statement.setInt(2,postid);
            statement.setString(1,username);
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
        }
        return config.getSrvName() + " OK";
    }

    public static int howmanylikes(int postid) {
        connection = mariadbConnect.mdbconn();
        int sol = 0;
        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT COUNT(*) AS 'CUANTOS', post FROM likes WHERE post = ?
                """)) {
            statement.setInt(1, postid);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()== false){return 0;} else {
                do {
                    sol = resultSet.getInt("CUANTOS");
                } while (resultSet.next());}
            connection.close();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD " + e);
        }
        return sol;
    }

}
