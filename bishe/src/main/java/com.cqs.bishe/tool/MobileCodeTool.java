package com.cqs.bishe.tool;

/**
 * Created by cqs on 16-6-6.
 */
public class MobileCodeTool {
    public static String makeMobileCode(String mobile){
        return mobile.substring(8)+System.nanoTime()%1000;
    }

   /* public static void main(String[] args) {
        System.out.println(makeMobileCode("15603366701"));
    }*/
}
