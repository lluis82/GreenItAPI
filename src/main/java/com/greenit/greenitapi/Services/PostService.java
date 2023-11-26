package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Util.Base64machine;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    String image = resultSet.getString("image");
                    String description = resultSet.getString("description");
                    post = new Post(creator, firstStep, id, servername, Base64machine.isBase64(image, id, 0, ""), description, image);
                    sol.add(post);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }

    public static Optional<Post> getPostById(int id){
        Optional<Post> optional;
        Post post = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM posts p
                    WHERE id = ?
                    LIMIT 1
                """)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){return null;} else {
                do {
                    User creator = UserService.getUserById(resultSet.getInt("creator")).orElse(null);
                    Step firstStep = null;
                    try {
                        int idstep = resultSet.getInt("firstStep");
                        firstStep = StepService.getStepById(idstep).orElse(null);
                    }catch(Exception e){}
                    String servername = resultSet.getString("serverName");
                    String image = resultSet.getString("image");
                    String description = resultSet.getString("description");
                    post = new Post(creator, firstStep, id, servername, Base64machine.isBase64(image, id, 0, ""), description, image);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(post);
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }

    public static String publishPost(String username, String description, String image) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO posts (servername, firstStep, creator, description, image) VALUES (?, null, (SELECT u.id from users u where u.username like ?), ?, ?)
                """)) {
            statement.setString(1,config.getSrvIp());
            statement.setString(2,username);
            statement.setString(3,description);
            statement.setString(4,image);
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return config.getSrvName() + " OK";
    }

    public static Optional<List<Post>> getAllPostsPaged(int page){
        Optional<List<Post>> optional;
        Post post = null;
        List<Post> sol = new ArrayList<>();
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM posts p
                    ORDER BY p.id
                    LIMIT ?,2
                """)) {
            statement.setInt(1,(2*page)-2);
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
                    String image = resultSet.getString("image");
                    String description = resultSet.getString("description");
                    post = new Post(creator, firstStep, id, servername, Base64machine.isBase64(image, id, 0, ""), description, image);
                    sol.add(post);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }

    public static int getCountOfUserPosts(String displayName){
        Optional<List<Post>> postsList = getPostByUser(displayName);
        int size = postsList.isPresent() ? postsList.get().size() : 0;
        return size;
    }
}
