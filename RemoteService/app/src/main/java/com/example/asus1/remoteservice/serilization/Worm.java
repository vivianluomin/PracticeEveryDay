package com.example.asus1.remoteservice.serilization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


class Data implements Serializable{


    private int i;
    Data(int x){
        i = x;
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}

public class Worm implements Serializable {

    private static int r(){
        return (int)(Math.random()*10);
    }

    private Data[] d = {
            new Data(r()),new Data(r()),new Data(r())
    };

    private Worm next;
    private char c;

    Worm(int i,char x){
        System.out.println("Worm constructor: "+i);
        c = x;
        if(--i>0){
            next = new Worm(i,(char)(x+1));
        }
    }

    Worm(){
        System.out.println("Default constructor");
    }

    @Override
    public String toString() {
        String s = ": "+c+"(";
        for(int i = 0;i<d.length;i++) {
            s += d[i].toString();
        }
            s+=")";
            if(next!=null){
                s+=next.toString();
            }

        return s;
    }

    public static void main(String[] args){
        Worm worm = new Worm(6,'a');
        System.out.println("worm = "+worm);
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream("worm.out"));
            outputStream.writeObject("Worm storage");
            outputStream.writeObject(worm);
            outputStream.close();

            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("worm.out"));
            String s = (String)inputStream.readObject();
            Worm worm1 = (Worm)inputStream.readObject();
            System.out.println(s+", w2 = "+worm1);

        }catch (FileNotFoundException e){

        }catch (IOException e){

        }catch (ClassNotFoundException e){

        }
    }

}
