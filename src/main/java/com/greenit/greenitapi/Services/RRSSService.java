package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Util.Config;
import org.springframework.stereotype.Service;
import java.io.File;
import java.sql.Connection;
import java.util.Scanner;

@Service
public class RRSSService {
    private static Connection connection;
    private static Config config = new Config();
    private static String getHTMLFile(String filename, int postid, int stepid, String username){
        String sol = "";
        try {
            Scanner input = new Scanner(new File(Config.getHTMLLocation() + filename));
            while (input.hasNextLine()) {
                sol += process(input.nextLine(), postid, stepid, username) + "\n";
            }
        } catch (Exception e) {
            return "No se pudo encontrar el html template para el nombre del server" + e;
        }
        return sol;
    }
    public static String getHTMLprofileFile(String username){
        return getHTMLFile("tempProfile.html", 0,0,username);
    }
    public static String getHTMLpostFile(int postid){
        return getHTMLFile("tempPost.html", postid, 0 ,"");
    }
    public static String getHTMLstepFile(int stepid){
        return getHTMLFile("tempStep.html",0,stepid,"");
    }
    private static String process(String inline, int postid, int stepid, String username){
        String sol = inline;
        //region POSTS
        if(inline.trim().contains("POST_HEADER"))
            sol = inline.replaceAll("<h2>POST_HEADER</h2>", "<h2>" + "</h2>");//borrar el texto de arriba
        if(inline.trim().contains("POST_TITLE"))
            sol = inline.replaceAll("<h1>POST_TITLE</h1>", "<h2>" + PostService.getPostById(postid).orElse(null).getCreator().getDisplayName() + "'s post</h2>");
        if(inline.trim().contains("POST_DESCRIPTION"))
            sol = inline.replaceAll("<p>POST_DESCRIPTION</p>", "<p>" + PostService.getPostById(postid).orElse(null).getDescription() + "</p>");
        if(inline.trim().contains("POST_IMAGE"))
            sol = inline.replaceAll("POST_IMAGE", "<img src=\"" + PostService.getPostById(postid).orElse(null).getImage() + "\" alt=\"Post Image\">");
        if(inline.trim().contains("POST_BUTTON")) {
            if(PostService.getPostById(postid).orElse(null).getFirstStep() != null)
            sol = inline.replaceAll("POST_BUTTON",
                    "<button onclick=\"location.href='http://localhost:8080/rsssstep?stepid=" + PostService.getPostById(postid).orElse(null).getFirstStep().getId() + "'\">Go to first step</button>");
            else sol = "";// si no tiene first step borramos el indicador
        }
        //endregion

        //region STEPS

        if(inline.trim().contains("POST_HEADER"))
            sol = inline.replaceAll("<h2>POST_HEADER</h2>", "<h2>" + "</h2>");
        //endregion

        //region PROFILES

        if(inline.trim().contains("POST_HEADER"))
            sol = inline.replaceAll("<h2>POST_HEADER</h2>", "<h2>" + "</h2>");
        //endregion


        return sol;
    }
}
