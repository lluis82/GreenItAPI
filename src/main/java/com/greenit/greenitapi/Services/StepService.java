package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Util.Base64machine;
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

                if(resultSet.next()== false){return null;} else {
                    do {
                        String desc = resultSet.getString("description");
                        int id = resultSet.getInt("id");
                        int prevStepID = resultSet.getInt("previousStep");
                        String image = resultSet.getString("image");
                        if (prevStepID == 0) step = new Step(desc, id, Base64machine.isBase64(image, 0, id, ""), image); else
                            step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null) , Base64machine.isBase64(image, 0, id, ""), image);
                        sol.add(step);
                    } while (resultSet.next());
                    connection.close();
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

                if(resultSet.next()== false){return null;} else {
                    do {
                        String desc = resultSet.getString("description");
                        int id = resultSet.getInt("id");
                        int prevStepID = resultSet.getInt("previousStep");
                        String image = resultSet.getString("image");
                        if (prevStepID == 0) step = new Step(desc, id, Base64machine.isBase64(image, 0, id, ""), image); else
                            step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null), Base64machine.isBase64(image, 0, id, ""), image);
                        sol.add(step);
                    } while (resultSet.next());
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("Error al recuperar info de la BD");
            }
            optional = Optional.of(sol);
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

            if(resultSet.next()== false){return null;} else {
                do {
                    String desc = resultSet.getString("description");
                    int id = resultSet.getInt("id");
                    int prevStepID = resultSet.getInt("previousStep");
                    String image = resultSet.getString("image");
                    if (prevStepID == 0) step = new Step(desc, id, Base64machine.isBase64(image, 0, id, ""), image); else
                        step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null), Base64machine.isBase64(image, 0, id, ""), image);
                } while (resultSet.next());
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(step);
        return optional;
    }


    public static String publishStep(int prevStepId, Boolean isFirst, String description, int postId, String image) {
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO step (description, previousStep, image) VALUES (?, null, ?)
                """)) {
            statement.setString(1,description);
            statement.setString(2,image);
            statement.executeQuery();

            if(isFirst){
                try (PreparedStatement statement2 = connection.prepareStatement("""
                    UPDATE posts set firstStep = (select s.id from step s where s.description like ?) WHERE id like ?
                """)) {
                    statement2.setString(1,description);
                    statement2.setInt(2,postId);
                    statement2.executeQuery();
                } catch (Exception e) {
                    System.out.println("Error al recuperar info de la BD");
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
                    return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL, Excepción: " + e.getMessage();
        }
        return config.getSrvName() + " OK";
    }
}
