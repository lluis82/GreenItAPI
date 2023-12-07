package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Controller.UserController;
import com.greenit.greenitapi.Entities.Comment;
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
public class CommentService {
    private static Connection connection;
    private static Config config = new Config();

    public static Optional<Comment> getCommentByID(int id) {
        Optional<Comment> optional;
        Comment comment = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM comments s
                    WHERE s.id like ?
                """)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) {
                try {
                    connection.close();
                    mariadbConnect.connclosed();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return null;
            } else {
                do {
                    String desc = resultSet.getString("text");
                    int prevStepID = resultSet.getInt("replyto");
                    //User creator = UserService.getUserById(resultSet.getInt("creator")).orElse(null);
                    User creator = UserController.getUserById(resultSet.getInt("creator"));
                    if (prevStepID != 0)
                        comment = new Comment(creator, id, desc, getCommentByID(prevStepID).orElse(null));
                    else
                        comment = new Comment(creator, id, desc, null);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(comment);
        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }

    public static Optional<List<Comment>> getCommentByPostID(int postid) {
        Optional<List<Comment>> optional;
        List<Comment> sol = new ArrayList<>();
        Comment comment = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM comments s
                    WHERE s.postid like ? and s.replyto is null
                """)) {

            statement.setInt(1, postid);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) {
                try {
                    connection.close();
                    mariadbConnect.connclosed();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return null;
            } else {
                do {
                    String desc = resultSet.getString("text");
                    int id = resultSet.getInt("id");
                    int prevStepID = resultSet.getInt("replyto");
                    //User creator = UserService.getUserById(resultSet.getInt("creator")).orElse(null);
                    User creator = UserController.getUserById(resultSet.getInt("creator"));
                    if (prevStepID != 0)
                        comment = new Comment(creator, id, desc, getCommentByID(prevStepID).orElse(null));
                    else
                        comment = new Comment(creator, id, desc, null);
                    sol.add(comment);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }

    public static Optional<List<Comment>> getRepliesToComment(int previd) {
        Optional<List<Comment>> optional;
        List<Comment> sol = new ArrayList<>();
        Comment comment = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM comments s
                    WHERE s.replyto like ?
                """)) {

            statement.setInt(1, previd);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() == false) {
                try {
                    connection.close();
                    mariadbConnect.connclosed();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return null;
            } else {
                do {
                    String desc = resultSet.getString("text");
                    int id = resultSet.getInt("id");
                    int prevStepID = resultSet.getInt("replyto");
                    //User creator = UserService.getUserById(resultSet.getInt("creator")).orElse(null);
                    User creator = UserController.getUserById(resultSet.getInt("creator"));
                    if (prevStepID != 0)
                        comment = new Comment(creator, id, desc, getCommentByID(prevStepID).orElse(null));
                    else
                        comment = new Comment(creator, id, desc, null);
                    sol.add(comment);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(sol);
        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }


    public static String publishComment(int prevCommentId, String text, int postid, String creatorName) {
        connection = mariadbConnect.mdbconn();

        if (prevCommentId > 0) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO comments (text, creator, replyto,postid) VALUES (?, ?, ?, ?)
                """)) {
            statement.setString(1,text);
            statement.setInt(2, UserController.getUserByName(creatorName).getId());
            statement.setInt(3, prevCommentId);
            statement.setInt(4,postid);

            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            try {
                connection.close();
                mariadbConnect.connclosed();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
        }
        }else{
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO comments (text, creator, replyto,postid) VALUES (?, ?, null, ?)
                """)) {
                statement.setString(1,text);
                statement.setInt(2, UserController.getUserByName(creatorName).getId());
                statement.setInt(3,postid);

                statement.executeQuery();
            } catch (Exception e) {
                System.out.println("Error al recuperar info de la BD");
                try {
                    connection.close();
                    mariadbConnect.connclosed();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
            }
        }

        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return config.getSrvName() + " OK";
    }
}

