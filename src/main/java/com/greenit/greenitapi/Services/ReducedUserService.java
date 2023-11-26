package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Controller.UserController;
import com.greenit.greenitapi.Entities.ReducedUser;
import com.greenit.greenitapi.Entities.User;
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
public class ReducedUserService {

    private static Connection connection;
    private static Config config = new Config();


    // id -> List<ReducedUser> pibe followea a los pibes de la lista (mirar codigo server/steps de pablo)
    public static Optional<List<ReducedUser>> getFollowedbyUser(int userId) {
        Optional<List<ReducedUser>> optional;
        List<ReducedUser> sol = new ArrayList<>();
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM Follows f
                    WHERE f.User = ?
                """)) {
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    int follows = resultSet.getInt("Follows");
                    //User u = UserService.getUserById(follows).orElse(null);
                    User u = UserController.getUserById(follows);
                    ReducedUser ru = new ReducedUser(follows, u.getDisplayName(), u.getImage(), u.getImagefield());
                    sol.add(ru);
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

    // id -> List<ReducedUser> lista de pibes que followea a id
    public static Optional<List<ReducedUser>> getUserFollowers(int userId) {
        Optional<List<ReducedUser>> optional;
        List<ReducedUser> sol = new ArrayList<>();
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM Follows f
                    WHERE f.Follows = ?
                """)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    int user = resultSet.getInt("User");
                    //User u = UserService.getUserById(user).orElse(null);
                    User u = UserController.getUserById(user);
                    ReducedUser ru = new ReducedUser(user, u.getDisplayName(), u.getImage(), u.getImagefield());
                    sol.add(ru);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        optional = Optional.of(sol);
        return optional;
    }

    // id -> empieza a followear a otra id
    public static String newFollower(int userId, int followedUserId) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO Follows (User, Follows) VALUES (?, ?)
                """)) {
            statement.setInt(1, userId);
            statement.setInt(2, followedUserId);
            ResultSet resultSet = statement.executeQuery();
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

    //id -> deja de followear a otra id
    public static String deleteFollower(int userId, int unfollowedUserId) {
        connection = mariadbConnect.mdbconn();
        try (PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM Follows WHERE User = ? AND Follows = ?
                """)) {
            statement.setInt(1, userId);
            statement.setInt(2, unfollowedUserId);
            ResultSet resultSet = statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al borrar info de la BD");
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

    public static boolean checkFollows(int userId, int followsUserId) {
        boolean follows = false;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT EXISTS (
                                SELECT 1
                                FROM Follows
                                WHERE User = ? AND Follows = ?
                            ) AS follows;
            """)) {
            statement.setInt(1, userId);
            statement.setInt(2, followsUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    follows = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error closing connection: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return follows;
    }
}
