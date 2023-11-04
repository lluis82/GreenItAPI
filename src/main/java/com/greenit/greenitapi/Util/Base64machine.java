package com.greenit.greenitapi.Util;

import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Services.PostService;
import com.greenit.greenitapi.Services.StepService;
import com.greenit.greenitapi.Services.UserService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Objects;

public class Base64machine {

    private static Config config = new Config();

    private static InputStream decodeBase64AsStream(String imgbase64){
        byte[] imageByte = Base64.getDecoder().decode(imgbase64);
            var bis = new ByteArrayInputStream(imageByte);
            return bis;
    }

    private static InputStream getImgfromInternet(String url){
        try {
            URLConnection openConnection = new URL(url).openConnection();
            openConnection.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

            return openConnection.getInputStream();
        }catch(Exception e){
            System.out.println("Algo fue mal obteniendo la img como stream de internet " + e);
            return InputStream.nullInputStream();}
    }

    public static InputStream getImgFromPost(int postid){
        Post u = PostService.getPostById(postid).orElse(null);
        String base64 = u.getImage();
        String base642 = u.getImagefield();
        if(Objects.equals(base64, "") || base64 == null){return getImgfromInternet("https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png");}
        if(base64.contains("http://") || base64.contains("https://")){
            if(!base64.contains(config.getSrvIp()))
                return getImgfromInternet(base64);}
        return decodeBase64AsStream(base642);
    }
    public static InputStream getImgFromStep(int stepid){
        Step u = StepService.getStepById(stepid).orElse(null);
        String base64 = u.getImage();
        String base642 = u.getImagefield();
        if(Objects.equals(base64, "") || base64 == null){return getImgfromInternet("https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png");}
        if(base64.contains("http://") || base64.contains("https://")){
            if(!base64.contains(config.getSrvIp()))
                return getImgfromInternet(base64);}
        return decodeBase64AsStream(base642);
    }
    public static InputStream getImgFromProfile(String username){
        User u = UserService.getUserByName(username).orElse(null);
        String base64 = u.getImage();
        String base642 = u.getImagefield();
        if(Objects.equals(base64, "") || base64 == null){return getImgfromInternet("https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png");}
        if(base64.contains("http://") || base64.contains("https://")){
            if(!base64.contains(config.getSrvIp()))
                return getImgfromInternet(base64);}
        return decodeBase64AsStream(base642);
    }

    public static String isBase64(String imgbase64, int postid, int stepid, String username){
        //realmente este solo tiene que decir ok, es un base64? no--> los dos ifs
        //si--> entonces le metemos un http de que pida al server por otro sitio

        if(Objects.equals(imgbase64, "") || imgbase64 == null){return "https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png";}
        if(imgbase64.contains("http://") || imgbase64.contains("https://")){return imgbase64;}
            if(postid != 0)
                return "http://" + config.getSrvIp() + "/getimgfrompostbyid?postid=" + postid;
            if(stepid != 0)
                return  "http://" + config.getSrvIp() + "/getimgfromstepbyid?stepid=" + stepid;
            return  "http://" + config.getSrvIp() + "/getimgfromprofilebyusername?username=" + username;
    }
}
