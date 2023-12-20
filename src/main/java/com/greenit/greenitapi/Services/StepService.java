package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Controller.StepController;
import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Util.Base64machine;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StepService {
    private static Connection connection;
    private static Config config = new Config();

    public static Optional<List<Step>> getStepByPrevId(int stepId) {
        Optional<List<Step>> optional;
        List<Step> sol= new ArrayList<>();
        Step step = null;
        connection = mariadbConnect.mdbconn();

        if(stepId == 0){
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM step s
                    WHERE s.previousStep IS NULL
                """)) {
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()== false){
                    try {
                        connection.close();
                        mariadbConnect.connclosed();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    return null;} else {
                    do {
                        String desc = resultSet.getString("description");
                        int id = resultSet.getInt("id");
                        int prevStepID = resultSet.getInt("previousStep");
                        String image = resultSet.getString("image");
                        if (prevStepID == 0) step = new Step(desc, id, Base64machine.isBase64(image, 0, id, ""), image); else
                            step = new Step(desc, id, StepController.getStepByid(resultSet.getInt("previousStep")) , Base64machine.isBase64(image, 0, id, ""), image);
                        sol.add(step);
                    } while (resultSet.next());
                }
            } catch (Exception e) {
                System.out.println("Error al recuperar info de la BD");
            }
            optional = Optional.of(sol);
        } else {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM step s
                    WHERE s.previousStep like ?
                """)) {
                statement.setInt(1, stepId);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()== false){
                    try {
                        connection.close();
                        mariadbConnect.connclosed();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    return null;} else {
                    do {
                        String desc = resultSet.getString("description");
                        int id = resultSet.getInt("id");
                        int prevStepID = resultSet.getInt("previousStep");
                        String image = resultSet.getString("image");
                        if (prevStepID == 0) step = new Step(desc, id, Base64machine.isBase64(image, 0, id, ""), image); else
                            step = new Step(desc, id, StepController.getStepByid(resultSet.getInt("previousStep")), Base64machine.isBase64(image, 0, id, ""), image);
                        sol.add(step);
                    } while (resultSet.next());
                }
            } catch (Exception e) {
                System.out.println("Error al recuperar info de la BD");
            }
            optional = Optional.of(sol);
        }
        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }

    public static Optional<Step> getStepById(int stepId) {
        Optional<Step> optional;
        Step step = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM step s
                    WHERE s.id like ?
                """)) {

            statement.setInt(1, stepId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){
                try {
                    connection.close();
                    mariadbConnect.connclosed();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return null;} else {
                do {
                    String desc = resultSet.getString("description");
                    int id = resultSet.getInt("id");
                    int prevStepID = resultSet.getInt("previousStep");
                    String image = resultSet.getString("image");
                    if (prevStepID == 0) step = new Step(desc, id, Base64machine.isBase64(image, 0, id, ""), image); else
                        step = new Step(desc, id, StepController.getStepByid(resultSet.getInt("previousStep")), Base64machine.isBase64(image, 0, id, ""), image);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(step);
        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return optional;
    }


    public static String publishStep(int prevStepId, Boolean isFirst, String description, int postId, String image) {
        connection = mariadbConnect.mdbconn();
        long id = 0;
        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO step (description, previousStep, image) VALUES (?, null, ?)
                """, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,description);
            statement.setString(2,image);
            statement.executeQuery();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
            if(isFirst){
                try (PreparedStatement statement2 = connection.prepareStatement("""
                    UPDATE posts set firstStep = (select s.id from step s where s.description like ?) WHERE id like ?
                """)) {
                    statement2.setString(1,description);
                    statement2.setInt(2,postId);
                    statement2.executeQuery();
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
                try (PreparedStatement statement2 = connection.prepareStatement("""
                    UPDATE step SET previousStep = ? WHERE description = ?
                """)) {
                    statement2.setInt(1,prevStepId);
                    statement2.setString(2,description);
                    statement2.executeQuery();
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
        try {
            connection.close();
            mariadbConnect.connclosed();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        //return config.getSrvName() + " OK";
        return id + "";
    }
}
