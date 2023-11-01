package com.greenit.greenitapi.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class Base64machine {

    public static String encode(String imgpath){
        return "";
    }

    public static String decode(String imgbase64){
        BufferedImage image = null;
        byte[] imageByte = Base64.getDecoder().decode(imgbase64);
        try {
            var bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException e) {
            System.out.println("hubo un problema decodificando la imagen");
        }
        File out = new File("/hola.png");
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            System.out.println("hubo un problema al guardar la imagen");
        }
        return out.getName();
    }

    public static String decode2(){
        BufferedImage image = null;
        File in = new File("src/main/resources/base64enc");
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(new Scanner(in).nextLine());
            var bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            File out = new File("src/main/resources/out");
            ImageIO.write(image, "PNG", out);
        } catch (Exception e) {
            System.out.println("hubo un problema decodificando base64");
        }
        return "src/main/resources/out";
    }
}
