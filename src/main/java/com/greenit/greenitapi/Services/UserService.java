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
                    FROM Users u
                    WHERE u.email like ?
                """)) {

            statement.setString(1, emailIn);
            //esto es para reemplazar el ? por el email
            //es muy cursed i know

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1); // by column index
                String name = resultSet.getString("userName"); // by column name
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = new User(name, email, password, config.getSrvName());
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
                    FROM Users u
                    WHERE u.username like ?
                """)) {

            statement.setString(1, username);
            //esto es para reemplazar el ? por el email
            //es muy cursed i know

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1); // by column index
                String name = resultSet.getString("userName"); // by column name
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = new User(name, email, password, config.getSrvName());
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
            //esto es para reemplazar el ? por el email
            //es muy cursed i know

            ResultSet resultSet = statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL";
        }
        return config.getSrvName() + " OK";
    }

}
