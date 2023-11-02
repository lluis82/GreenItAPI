package com.greenit.greenitapi.Util;

import com.greenit.greenitapi.Services.PostService;
import com.greenit.greenitapi.Services.StepService;
import com.greenit.greenitapi.Services.UserService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

public class Base64machine {

    private static Config config = new Config();

    private static InputStream decodeBase64AsStream(String imgbase64){
        byte[] imageByte = Base64.getDecoder().decode(imgbase64);
            var bis = new ByteArrayInputStream(imageByte);
            return bis;
    }

    public static InputStream getImgFromPost(int postid){
        String base64 = PostService.getPostById(postid).orElse(null).getImage();
        return decodeBase64AsStream(base64);
    }
    public static InputStream getImgFromStep(int stepid){
        String base64 = StepService.getStepById(stepid).orElse(null).getImage();
        return decodeBase64AsStream(base64);
    }
    public static InputStream getImgFromProfile(String username){
        String base64 = UserService.getUserByName(username).orElse(null).getImage();
        return decodeBase64AsStream(base64);
    }

    public static String isBase64(String imgbase64, int postid, int stepid, String username){
        //realmente este solo tiene que decir ok, es un base64? no--> los dos ifs
        //si--> entonces le metemos un http de que pida al server por otro sitio

        if(Objects.equals(imgbase64, "") || imgbase64 == null){return "https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png";}
        if(imgbase64.contains("http://") || imgbase64.contains("https://")){return imgbase64;}
        if(Config.inDebug()){
            if(postid != 0)
                return "http://localhost:8080/getimgfrompostbyid?postid=" + postid;
            if(stepid != 0)
                return  "http://localhost:8080/getimgfromstepbyid?stepid=" + stepid;
            return  "http://localhost:8080/getimgfromprofilebyusername?username=" + username;
        }else{
            if(postid != 0)
                return "http://" + config.getSrvIp() + "/getimgfrompostbyid?postid=" + postid;
            if(stepid != 0)
                return  "http://" + config.getSrvIp() + "/getimgfromstepbyid?stepid=" + stepid;
            return  "http://" + config.getSrvIp() + "/getimgfromprofilebyusername?username=" + username;
        }
    }
}
