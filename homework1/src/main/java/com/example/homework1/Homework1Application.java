package com.example.homework1;

import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.HashMap;

public class Homework1Application {

    public static void main(String[] args) {
        String[] strings = new String[7];
        strings[0] = "1";
        strings[1] = "2";
        strings[2] = "3";
        strings[3] = "4";
        strings[4] = "5";
        strings[5] = null;
        strings[6] = null;

        System.out.println(strings[2].equals("3"));

        Arrays.stream(strings).forEach(l -> System.out.println(l + ", "));
        System.out.println("____________");
        boolean swipe = false;
        for (int i = 0 ; i < strings.length; i++){
            if(strings[i] != null && strings[i].equals("3") && !swipe){
                strings[i] = null;
                if(i + 1 < 5)swipe = true;
                else break;
            }
            if(swipe && i + 1 < strings.length && strings[i + 1] != null){
                String temp = strings[i + 1];
                strings[i] = temp;
                strings[i + 1] = null;
                continue;
            }
        }

        Arrays.stream(strings).forEach(l -> System.out.println(l + ", "));

    }

}
