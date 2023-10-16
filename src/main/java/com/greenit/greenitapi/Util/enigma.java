package com.greenit.greenitapi.Util;

public class enigma {

    public static String decode(String servername, String auxcode){
        int sumchar = 0;
        String aux = auxcode;
        double ip1=0;
        double ip2=0;
        double ip3=0;
        double ip4=0;

        for(int i = 0; i< servername.length(); i++){
            sumchar += servername.charAt(i);
        }
        sumchar /= 4;

        ip1 = auxParse(aux) * 127;
        aux = auxConsume(aux);
        ip2 = auxParse(aux) * 255;
        aux = auxConsume(aux);
        ip3 = auxParse(aux) * 255;
        aux = auxConsume(aux);
        ip4 = auxParse(aux) * 255;

        ip1 = sumchar - ip1;
        ip2 = sumchar - ip2;
        ip3 = sumchar - ip3;
        ip4 = sumchar - ip4;

        String sol = (int)ip1 + "." + (int)ip2 + "." + (int)ip3 + "." + (int)ip4;
        return sol;
    }

    public static String encode(String servername, String ip){
        int sumchar = 0;
        String ip0 = ip;
        int ip1=0;
        int ip2=0;
        int ip3=0;
        int ip4=0;

        for(int i = 0; i< servername.length(); i++){
            sumchar += servername.charAt(i);
        }
        sumchar /= 4;

        ip1 = ipParse(ip0);
        ip0 = ipConsume(ip0);
        ip2 = ipParse(ip0);
        ip0 = ipConsume(ip0);
        ip3 = ipParse(ip0);
        ip0 = ipConsume(ip0);
        ip4 = ipParse(ip0);

        ip1 = sumchar - ip1;
        ip2 = sumchar - ip2;
        ip3 = sumchar - ip3;
        ip4 = sumchar - ip4;

        double sol1 = ip1 / 127.0;
        double sol2 = ip2 / 255.0;
        double sol3 = ip3 / 255.0;
        double sol4 = ip4 / 255.0;


        String sol = sol1 + "," + sol2 + "," + sol3 + "," + sol4;

        return sol;
    }

    public static int ipParse(String ip){
        String sol = "";
        int b = 0;
        while(b < ip.length() && ip.charAt(b) != '.'){
            sol += ip.charAt(b);
            b++;
        }
        return Integer.parseInt(sol);
    }

    public static String ipConsume(String ip){
        int b = 0;
        while(b < ip.length() && ip.charAt(b) != '.'){
            b++;
        }
        return ip.substring(++b);
    }

    public static double auxParse(String auxcode){
        String sol = "";
        int b = 0;
        while(b < auxcode.length() && auxcode.charAt(b) != ','){
            sol += auxcode.charAt(b);
            b++;
        }
        return Double.parseDouble(sol);
    }

    public static String auxConsume(String auxcode){
        int b = 0;
        while(b < auxcode.length() && auxcode.charAt(b) != ','){
            b++;
        }
        return auxcode.substring(++b);
    }


}
