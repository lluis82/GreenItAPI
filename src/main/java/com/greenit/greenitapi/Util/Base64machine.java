package com.greenit.greenitapi.Util;

import com.greenit.greenitapi.Services.PostService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;

public class Base64machine {

    public static String decodeAsFile(String imgbase64){
        if(Objects.equals(imgbase64, "") || imgbase64 == null){return "https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png";}
        if(imgbase64.contains("http://") || imgbase64.contains("https://")){return imgbase64;}
        BufferedImage image = null;
        byte[] imageByte = Base64.getDecoder().decode(imgbase64);
        try {
            var bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
            System.out.println("hubo un problema decodificando la imagen");
        }
        File out = new File( Config.getHTMLrootImgLocation() + "hola.png");
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            System.out.println("hubo un problema al guardar la imagen");
        }
        return out.getName();
    }

    private static InputStream decodeBase64AsStream(String imgbase64){
        byte[] imageByte = Base64.getDecoder().decode(imgbase64);
            var bis = new ByteArrayInputStream(imageByte);
            return bis;
    }

    public static InputStream getImgFromPost(int postid){
        String base64 = PostService.getPostById(postid).orElse(null).getImage();
        return decodeBase64AsStream(base64);
    }

    public static String isBase64post(String imgbase64, int postid){
        //realmente este solo tiene que decir ok, es un base64? no--> los dos ifs
        //si--> entonces le metemos un http de que pida al server por otro sitio

        if(Objects.equals(imgbase64, "") || imgbase64 == null){return "https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png";}
        if(imgbase64.contains("http://") || imgbase64.contains("https://")){return imgbase64;}
        if(Config.inDebug())
            return "http://localhost:8080/getimgfrompostbyid?postid=" + postid;
        return "http://13.49.72.206/getimgfrompostbyid?postid=" + postid;
    }

    //VVVVVVVVVVVV testing interno only, borrar al final VVVVVVVVVVVV
    public static String decode2(){
        BufferedImage image = null;
        File in = new File( Config.getResourcesLocation() + "base64enc");
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(new Scanner(in).nextLine());
            var bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            File out = new File(Config.getResourcesLocation() + "out");
            ImageIO.write(image, "PNG", out);
        } catch (Exception e) {
            System.out.println("hubo un problema decodificando base64");
        }
        return  Config.getResourcesLocation() + "out";
    }
}
