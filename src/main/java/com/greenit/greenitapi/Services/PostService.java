package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Entities.Server;
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
public class PostService {
    private static Connection connection;
    private static Config config = new Config();

    public static Optional<List<Post>> getPostByUser(String username){
        Optional<List<Post>> optional;
        Post post = null;
        List<Post> sol = new ArrayList<>();
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM posts p
                    WHERE creator like (SELECT u.id FROM users u WHERE u.userName like ?)
                """)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){return null;} else {
                do {
                    User creator = UserService.getUserByName(username).orElse(null);
                    Step firstStep = null;
                    int id = resultSet.getInt("id");
                    try {
                        int idstep = resultSet.getInt("firstStep");
                        firstStep = StepService.getStepById(idstep).orElse(null);

                    }catch(Exception e){}
                    String servername = resultSet.getString("serverName");
                    post = new Post(creator, firstStep, id, servername);
                    sol.add(post);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        return optional;

    }

    public static String publishPost(String username) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO posts (servername, firstStep, creator) VALUES (?, null, (SELECT u.id from users u where u.username like ?))
                """)) {
            statement.setString(1,config.getSrvIp());
            statement.setString(2,username);
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL";
        }
        return config.getSrvName() + " OK";
    }

    public static Optional<List<Post>> getAllPosts(){
        Optional<List<Post>> optional;
        Post post = null;
        List<Post> sol = new ArrayList<>();
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM posts p
                """)) {
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){return null;} else {
                do {
                    User creator = UserService.getUserById(resultSet.getInt("creator")).orElse(null);
                    Step firstStep = null;
                    int id = resultSet.getInt("id");
                    try {
                        int idstep = resultSet.getInt("firstStep");
                        firstStep = StepService.getStepById(idstep).orElse(null);

                    }catch(Exception e){}
                    String servername = resultSet.getString("serverName");
                    post = new Post(creator, firstStep, id, servername);
                    sol.add(post);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        return optional;

    }


}
