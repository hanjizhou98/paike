package com.zj.demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomName {

    public static String getRandomName(Integer num){
        String rn = "";
        for(int i=0;i<num;i++){
            int t = new Random().nextInt(26);
            if (t%2==0){
                char c = (char)(65+t);
                rn += c;
            }else{
                char c = (char)(97+t);
                rn += c;
            }
        }
        return rn;
    }


    public static ArrayList<String> getNameList() {
        String root = "C:\\Users\\zhou\\Desktop\\";
        String out = "xm.txt";
        ArrayList<String> names = new ArrayList<>();
        try {
            File f = new File(root + out);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            System.out.println("Reading file using Buffered Reader");
            while ((readLine = b.readLine()) != null) {
                names.add(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(names.toString());
        return names;
    }

    public static String[] getBookNameAndAuthor(Integer num){
        String[] na = new String[2];
        ArrayList<String> nl = getNameList();
        String temp = nl.get(num);
        na = temp.split(" ");
        return na;
    }

    public static String getXM(Integer num){
        ArrayList<String> nl = getNameList();
        return nl.get(num);
    }

    public static String getRandomMajor(){
        String major = "";
        String[] majors = {"哲学","法学","教育学","文学","经济学"
                ,"历史学","理学","工学","医学","管理学","艺术学","农学"};
        major = majors[new Random().nextInt(majors.length)];
        return major;
    }

    public static String getRandomType(){
        String type = "";
        String[] types = {"中国名著","世界名著"};
        return types[new Random().nextInt(types.length)];
    }
}
