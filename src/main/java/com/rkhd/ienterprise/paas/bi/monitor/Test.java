package com.rkhd.ienterprise.paas.bi.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public class Test {

    public static void main(String args[]){

       Integer ss[] = new Integer[] {5,3, 0, 0, 0, 7, 10, 15, 23, 30,33, 16,25,34,40,44,27,20,16,15,10,11,7,6};
       System.out.println(ss.length);

       for(int i=0;i<ss.length;i++){
         System.out.print((int)(getRandom() * ss[i])+",");
       }
    }

    public static double getRandom(){
        int max=10;
        int min=0;
        Random random = new Random();

        double m = random.nextInt(max)%(max-min+1) + min;
        return m/100;
    }
}
