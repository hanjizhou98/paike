package com.zj.demo.utils;

import java.util.Random;

public class GetString {

    public String getString(){
        // A-Z 65-90
        String out = "";
        for(int i=0;i<10;i++){
            int index = new Random().nextInt(26)+65;
            char c = (char)index;
            out += c+"";
        }
        return out;
    }
}
