package com.greenit.greenitapi.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class Config {
    //Sí, he copiado esta clase entera del proyecto de PSW y no tengo ninguna clase de remordimiento
    //cuidado con las telarañas y con los comentarios anticuados :)

    /* copia de properties por si acaso
    *
OWNNAME= Touka
MARIADB= jdbc:mariadb://localhost:3306/merequetengue
MDBUSER= root
MDBPASS= adre1234
    *
    * */

    Properties properties = new Properties();
    File file = new File("src/main/resources/server.properties");
    //Si añades algún atributo, describe añadiendo a este string qué valores debe aceptar
    //actualizar
    String comment = "SELECTOR admite los siguientes valores:\n" +
            "#TEST -> SOLO PREGUNTAS TIPO TEST\n" +
            "#SOPA -> SOLO PREGUNTAS SOPA\n" +
            "#RANDOM -> PREGUNTAS ALEATORIAS, DE ENTRE TODOS LOS TIPOS\n" +
            "#AHORCADO -> SOLO PREGUNTAS AHORCADO\n" +
            "LONGITUD admite CORTA o LARGA, POR DEFECTO ES LARGA\n" +
            "Volumen admite valores de 0.0 a 1.0\n";
    public Config(){
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //SI ESTO DA UNA EXCEPCION ES PORQUE EL ARCHIVO NO EXISTE
        }
        /*
        if(getVolumen() > 1.0 || 0.0 > getVolumen()){setVolumen(0.3);}//valor por defecto de sonido
        if(!availableStrategies().contains(getSelector())){setSelector("RANDOM");}//valor por defecto de selector de retos
        if(!availableLengths().contains(getLength())){setLength("LARGA");}//valor por defecto de longitud de partida
        */ //actualizar
    }
    /*
    Si añades más valores a guardar, recuerda hacer un get y set como los de abajo
    SI ALGUN GET O SET DA EXCEPCION ES PORQUE LA CLAVE (AKA "VOLUMEN" O "SELECTOR") NO EXISTE
     */


    public String getSrvName(){
        return (String)properties.get("OWNNAME");
    }
    public void setSrvName(double newName){
        properties.setProperty("OWNNAME",""+newName);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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
    public String getIMGURI(String username){//soporte para imagenes en el futuro
        return (String)properties.get(username);
    }

    public void setIMGURI(String username, String URI){
        properties.setProperty(username,URI);
        try {
            saveProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }











}
