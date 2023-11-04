package com.greenit.greenitapi.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
public class Config {
    //Sí, he copiado esta clase entera del proyecto de PSW y no tengo ninguna clase de remordimiento

    Properties properties = new Properties();
    File file;
    //Si añades algún atributo, describe añadiendo a este string qué valores debe aceptar
    String comment =
            "#ARCHIVO DE CONFIG DE SERVER GREENIT API\n" +
            "#OWNNAME -> El nombre del servidor\n" +
            "#PUBLICIP -> La IP pública del servidor\n" +
            "#MARIADB -> La URL del servidor de MariaDB\n" +
            "#MDBUSER -> El usuario para mariaDB\n" +
            "#MDBPASS -> La contraseña para mariaDB\n\n\n";
    public Config(){
        try {
            if(System.getProperty("os.name").equals("Mac OS X")   ||
               System.getProperty("os.name").equals("Windows 10") ||
               System.getProperty("os.name").equals("Windows 11"))
                {file = new File("src/main/resources/server.properties");}
            else{file = new File("/home/ubuntu/app/server.properties");}
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //SI ESTO DA UNA EXCEPCION ES PORQUE EL ARCHIVO NO EXISTE
        }

        //region DEFAULT
        if(getSrvName() == null ){setSrvName("Touka");}
        if(getMdbPass() == null ){setMdbPass("adre1234");}
        if(getMdbUser() == null ){setMdbUser("root");}
        if(getSrvIp() == null ){setSrvIp("127.0.0.1");}
        if(getMdbURL() == null ){setMdbURL("jdbc:mariadb://localhost:3306/merequetengue");}
        //endregion
    }
    /*
    Si añades más valores a guardar, recuerda hacer un get y set como los de abajo
    SI ALGUN GET O SET DA EXCEPCION ES PORQUE LA CLAVE (AKA "OWNNAME" O "MDBPASS" ETC) NO EXISTE
    */

    //region Server
    public String getSrvName(){
        return (String)properties.get("OWNNAME");
    }
    public void setSrvName(String newName){
        properties.setProperty("OWNNAME",""+newName);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSrvIp(){
        if(inDebug()) return "localhost:8080";
        return (String)properties.get("PUBLICIP");
    }
    public void setSrvIp(String newIP){
        properties.setProperty("PUBLICIP",""+newIP);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion

    //region MARIADB
    public String getMdbPass(){
        return (String)properties.get("MDBPASS");
    }
    public void setMdbPass(String newPass){
        properties.setProperty("MDBPASS",""+newPass);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getMdbUser(){
        return (String)properties.get("MDBUSER");
    }
    public void setMdbUser(String newUser){
        properties.setProperty("MDBUSER",""+newUser);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getMdbURL(){
        return (String)properties.get("MARIADB");
    }
    public void setMdbURL(String newURL){
        properties.setProperty("MARIADB",""+newURL);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region UTILS
    private void saveProperties() throws IOException
    {
        FileOutputStream fr = new FileOutputStream(file);
        properties.store(fr, comment);
        fr.close();
    }
    private void loadProperties()throws IOException
    {
        FileInputStream fi=new FileInputStream(file);
        properties.load(fi);
        fi.close();
    }
    //HAY QUE TOCAR ESTO PARA QUE EN LINUX SEA DIFERENTE PERO NO LO VOY A HACER AHORA
    public static Boolean inDebug(){
        if(Objects.equals(System.getProperty("os.name"), "Linux")){return false;} else return true;
    }
    public static String getHTMLLocation(){if(inDebug()){return "src/main/resources/html/";}else return "/home/ubuntu/app/html/";}
    public static String getResourcesLocation(){if(inDebug()){return "src/main/resources/";}else return "/home/ubuntu/app/";}

    //endregion

}
