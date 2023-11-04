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
                    post = new Post(creator, firstStep, id, servername, image, description);
                    sol.add(post);
                } while (resultSet.next());
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);

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
                    post = new Post(creator, firstStep, id, servername, image, description);
                } while (resultSet.next());
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(post);
        return optional;
    }

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
