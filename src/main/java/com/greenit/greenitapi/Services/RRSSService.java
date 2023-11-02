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
            Scanner input = new Scanner(new File(Config.getHTMLLocation() + "tempPost.html"));
            while (input.hasNextLine()) {
                //hacer comprobaciones por si tienes que sustituir por imagenes etc?
                String inline = input.nextLine();
                String inline2 = process(inline);
                sol += inline2 + "\n";
            }
        } catch (Exception e) {
            return "No se pudo encontrar el html template para el nombre del server";
        }
        return sol;
    }
    private static String process(String inline){
        //System.out.println(inline);
        String sol = inline;
        //esto hay que hacerlo por separado o con ifs o switch o algoç
        if(inline.trim().startsWith("<title>"))
        sol = inline.replaceAll("<title>.*</title>", "<title>GreenIT Post</title>");
        if(inline.trim().startsWith("<h2>"))
        sol = inline.replaceAll("<h2>.*</h2>", "<h2>GreenIT Post</h2>");
        if(inline.trim().startsWith("<h1>"))
        sol = inline.replaceAll("<h1>.*</h1>", "<h1>Titulo del post</h1>");
        //System.out.println(sol);
        return sol;
    }
}
