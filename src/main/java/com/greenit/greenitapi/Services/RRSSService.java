package com.greenit.greenitapi.Services;

import com.greenit.greenitapi.Controller.PostController;
import com.greenit.greenitapi.Controller.StepController;
import com.greenit.greenitapi.Controller.UserController;
import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Util.Base64machine;
import com.greenit.greenitapi.Util.Config;
import org.springframework.stereotype.Service;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
            return "No se pudo encontrar el html template para el nombre del server " + e;
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
        if(inline.trim().contains("POST_TITLE"))
            sol = inline.replaceAll("<h1>POST_TITLE</h1>", "<h1>" + PostController.getPostById(postid).getCreator().getDisplayName() + "'s post</h1>");
        if(inline.trim().contains("POST_DESCRIPTION"))
            sol = inline.replaceAll("<p>POST_DESCRIPTION</p>", "<p>" + PostController.getPostById(postid).getDescription() + "</p>");
        if(inline.trim().contains("POST_IMAGE"))
            sol = inline.replaceAll("POST_IMAGE", "<img src=\"" + Base64machine.isBase64(PostService.getPostById(postid).orElse(null).getImage(), postid, 0, "") + "\" alt=\"Post Image\">");
        if(inline.trim().contains("POST_BUTTON")) {
            if(PostService.getPostById(postid).orElse(null).getFirstStep() != null)
            sol = inline.replaceAll("POST_BUTTON",
                    "<button onclick=\"location.href='http://" + config.getSrvIp() + "/rrssstep?stepid=" + PostController.getPostById(postid).getFirstStep().getId() + "'\">Go to first step</button>");
            else sol = "";// si no tiene first step borramos el indicador
        }
        //endregion

        //region STEPS
        if(inline.trim().contains("STEP_IMAGE"))
            sol = inline.replaceAll("STEP_IMAGE", "<img src=\"" + Base64machine.isBase64(StepService.getStepById(stepid).orElse(null).getImage(), 0, stepid, "") + "\" alt=\"Step Image\">");
        if(inline.trim().contains("STEP_DESCRIPTION"))
            sol = inline.replaceAll("<p>STEP_DESCRIPTION</p>", "<p>" + StepController.getStepByid(stepid).getDescription() + "</p>");
        if(inline.trim().contains("STEP_PREVIOUS")) {
            if(StepService.getStepById(stepid).orElse(null).getPreviousStep() != null)
                sol = inline.replaceAll("STEP_PREVIOUS",
                        "<button onclick=\"location.href='http://" + config.getSrvIp() + "/rrssstep?stepid=" + StepController.getStepByid(stepid).getPreviousStep().getId() + "'\">Go to previous step</button>");
            else sol = "";// si no tiene prev step borramos el indicador
        }
        if(inline.trim().contains("STEP_CONTAINER")){
            try {
                sol = "";
                List<Step> a;
                try{
                 a = StepController.getStepByPrevId(stepid);}catch (Exception e)
                {a = new ArrayList<>();}
                while(a.size()>0){
                    Step b = a.remove(0);
                    sol += "        <div class=\"container\">\n" +
                            "            <img src=\"" + Base64machine.isBase64(b.getImage(), 0, b.getId(), "")+"\" alt=\"Image 3\">\n" +
                            //"            <p>This is some text for container 1.</p>\n" +
                            "            <button onclick=\"location.href='http://"+ config.getSrvIp() + "/rrssstep?stepid=" + b.getId() + "'\">Follow this step next</button>\n" +
                            "        </div>\n";
                }
            }catch(Exception e){}
        }
        //endregion

        //region PROFILES
        if(inline.trim().contains("PROFILE_IMAGE"))
            sol = inline.replaceAll("PROFILE_IMAGE", "<img src=\"" + Base64machine.isBase64(UserService.getUserByName(username).orElse(null).getImage(), 0, 0, username) + "\" alt=\"Profile Image\">");
        if(inline.trim().contains("PROFILE_DESCRIPTION"))
            sol = inline.replaceAll("<p>PROFILE_DESCRIPTION</p>", "<p>" + UserController.getUserByName(username).getDescription() + "</p>");
        if(inline.trim().contains("PROFILE_USERNAME"))
            sol = inline.replaceAll("<p>PROFILE_USERNAME</p>", "<p>" + UserController.getUserByName(username).getDisplayName() + "</p>");
        if(inline.trim().contains("PROFILE_NAME"))
            sol = inline.replaceAll("<h1>PROFILE_NAME</h1>", "<h1>" + UserController.getUserByName(username).getDisplayName() + "'s profile</h1>");
        if(inline.trim().contains("PROFILE_CONTAINER")){
            List<Post> a;
            try{
                a = PostController.getPostByUser(username);}catch (Exception e)
            {a = new ArrayList<>();}
            sol = "";
            while(a.size()>0){
                Post b = a.remove(0);
                sol += "        <div class=\"container\">\n" +
                        "            <img src=\"" + Base64machine.isBase64(b.getImage(), b.getId(), 0, "")+"\" alt=\"Image 3\">\n" +
                        //"            <p>This is some text for container 1.</p>\n" +
                        "            <button onclick=\"location.href='http://"+ config.getSrvIp() + "/rrsspost?postid=" + b.getId() + "'\">See this post</button>\n" +
                        "        </div>\n";
            }
        }
        //endregion


        return sol;
    }
}
