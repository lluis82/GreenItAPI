package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Service
public class UserService {
    private static Connection connection;
    private static Config config = new Config();

    public static Optional<User> getUser(String emailIn) {
        Optional<User> optional;
        User user = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM users u
                    WHERE u.email like ?
                """)) {

            statement.setString(1, emailIn);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()== false){return null;} else {
                do {
                    String name = resultSet.getString("userName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    user = new User(name, email, password, config.getSrvName());
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(user);
        return optional;
    }

    public static Optional<User> getUserById(int id) {
        Optional<User> optional;
        User user = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM users u
                    WHERE u.id like ?
                """)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){return null;} else {
                do {
                    String name = resultSet.getString("userName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    System.out.println(name + " " + email + " " + password + " " + config.getSrvName());
                    user = new User(name, email, password, config.getSrvName());
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(user);
        return optional;
    }

    public static Optional<User> getUserByName(String username) {
        Optional<User> optional;
        User user = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM users u
                    WHERE u.username like ?
                """)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){return null;} else {
                do {
                    String name = resultSet.getString("userName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    System.out.println(name + " " + email + " " + password + " " + config.getSrvName());
                    user = new User(name, email, password, config.getSrvName());
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(user);
        return optional;
    }


    public static String register(String emailIn, String password, String username) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO users (email, userName, password) VALUES (?, ?, ?)
                """)) {
            statement.setString(1,emailIn);
            statement.setString(2,username);
            statement.setString(3,password);
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL";
        }
        return config.getSrvName() + " OK";
    }

}
