package com.zj.demo.utils;

import java.util.Locale;
import java.util.UUID;

public class IDGenerator {

    public static String getUniqueID(){
        return UUID.randomUUID().toString().replaceAll("-","").
                toUpperCase(Locale.ROOT).substring(22);
    }

    public static String getID(int num){
        String id = "";
        if ((num)<10){
            id+="000";
            id+=(num);
        }else if ((num)<100){
            id+="00";
            id+=(num);
        }else if ((num)<1000){
            id+="0";
            id+=(num);
        }else{
            id+=(num);
        }
        return id;
    }
}
