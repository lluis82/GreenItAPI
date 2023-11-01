package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Util.Config;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.util.Scanner;

@Service
public class RRSSService {
    private static Connection connection;
    private static Config config = new Config();

    public static String getHTMLFile(){
        String sol = "";
        try {
            Scanner input = new Scanner(new File("src/main/resources/html/temp2.html"));
            while (input.hasNextLine()) {
                //hacer comprobaciones por si tienes que sustituir por imagenes etc?
                sol += input.nextLine() + "\n";
            }
        } catch (Exception e) {
            return "No se pudo encontrar el html template para el nombre del server";
        }
        return sol;
    }


}
