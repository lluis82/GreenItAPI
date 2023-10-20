package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Service
public class StepService {
    private static Connection connection;
    private static Config config = new Config();

    public static Optional<Step> getStepByPrevId(int stepId) {
        Optional<Step> optional;
        Step step = null;
        connection = mariadbConnect.mdbconn();

        if(stepId == 0){
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM Step s
                    WHERE s.previousStep IS NULL
                """)) {
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()== false){return null;} else {
                    do {
                        String desc = resultSet.getString("description");
                        int id = resultSet.getInt("id");
                        int prevStepID = resultSet.getInt("previousStep");
                        if (prevStepID == 0) step = new Step(desc, id); else
                            step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null));
                    } while (resultSet.next());
                }
            } catch (Exception e) {
                System.out.println("Error al recuperar info de la BD");
            }
            optional = Optional.of(step);
        } else {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM Step s
                    WHERE s.previousStep like ?
                """)) {
                statement.setInt(1, stepId);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()== false){return null;} else {
                    do {
                        String desc = resultSet.getString("description");
                        int id = resultSet.getInt("id");
                        int prevStepID = resultSet.getInt("previousStep");
                        if (prevStepID == 0) step = new Step(desc, id); else
                            step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null));
                    } while (resultSet.next());
                }
            } catch (Exception e) {
                System.out.println("Error al recuperar info de la BD");
            }
            optional = Optional.of(step);
        }
        return optional;
    }

    public static Optional<Step> getStepById(int stepId) {
        Optional<Step> optional;
        Step step = null;
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT *
                    FROM Step s
                    WHERE s.id like ?
                """)) {

            statement.setInt(1, stepId);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()== false){return null;} else {
                do {
                    String desc = resultSet.getString("description");
                    int id = resultSet.getInt("id");
                    int prevStepID = resultSet.getInt("previousStep");
                    if (prevStepID == 0) step = new Step(desc, id); else
                        step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null));
                } while (resultSet.next());
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(step);
        return optional;
    }


    public static String publishStep(int prevStepId, Boolean isFirst, String description, int postId) {
        connection = mariadbConnect.mdbconn();

        try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO step (description, previousStep) VALUES (?, null)
                """)) {
            statement.setString(1,description);
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
                    return config.getSrvName() + " FAIL";
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
                    return config.getSrvName() + " FAIL";
                }
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
            return config.getSrvName() + " FAIL";
        }
        return config.getSrvName() + " OK";
    }
}
