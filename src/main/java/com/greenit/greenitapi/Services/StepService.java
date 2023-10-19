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
            //esto es para reemplazar el ? por el email
            //es muy cursed i know

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String desc = resultSet.getString("description");
                int id = resultSet.getInt("id");
                int prevStepID = resultSet.getInt("previousStep");
                if (prevStepID == 0) step = new Step(desc, id); else
                step = new Step(desc, id, getStepById(resultSet.getInt("previousStep")).orElse(null));
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar info de la BD");
        }
        optional = Optional.of(step);
        return optional;
    }

}
