package com.example.asus1.rexiufu;

public class DEX {

    int a = 0;
    static String b = "HelloDalvik";

    public int getNumber(int i,int j){
        int e = 3;
        return e+i+j;
    }

    public static void main(String[] args){
        int c= 1;
        int d = 2;
        DEX dex = new DEX();
        String sayNumber = String.valueOf(dex.getNumber(c,d));
        System.out.println("HelloDalVik "+sayNumber);
    }
}
