package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Util.Base64machine;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("userName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String image = resultSet.getString("image");
                    String description = resultSet.getString("description");
                    user = new User(id, name, email, password, config.getSrvName(), Base64machine.isBase64(image, 0, 0, name), description, image);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(user);
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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

            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    String name = resultSet.getString("userName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String image = resultSet.getString("image");
                    String description = resultSet.getString("description");
                    user = new User(id, name, email, password, config.getSrvName(), Base64machine.isBase64(image, 0, 0, name), description, image);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(user);
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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

            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("userName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String image = resultSet.getString("image");
                    String description = resultSet.getString("description");
                    user = new User(id, name, email, password, config.getSrvName(), Base64machine.isBase64(image, 0, 0, name), description, image);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(user);
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }


    public static String register(String emailIn, String password, String username, String image, String description) {
        connection = mariadbConnect.mdbconn();
        if (getUserByName(username) != null) {
            return config.getSrvName() + " FAIL, ya existe un usuario con ese nombre";
        }
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO users (email, userName, password, image, description) VALUES (?, ?, ?, ?, ?)
                """)) {
            statement.setString(1, emailIn);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, image);
            statement.setString(5, description);
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

    public static String updateUser(int userId, String emailIn, String password, String username, String image, String description) {
        try (Connection connection = mariadbConnect.mdbconn()) {
            if (getUserById(userId) == null) {
                return config.getSrvName() + " FAIL, no existe un usuario con ese id";
            }

            try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE users SET email = ?, userName = ?, password = ?, image = ?, description = ? WHERE id = ?
                """)) {
                statement.setString(1, emailIn);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, image);
                statement.setString(5, description);
                statement.setInt(6, userId);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0 && rowsAffected < 2) {
                    System.out.println("User updated successfully.");
                } else {
                    System.out.println("User not found or no change made.");
                }
            } catch (Exception e) {
                System.out.println("Error al updatear info de la BD: " + e.getMessage());
                e.printStackTrace();
                return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD: " + e.getMessage());
            e.printStackTrace();
            return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return config.getSrvName() + " OK";
    }

}
