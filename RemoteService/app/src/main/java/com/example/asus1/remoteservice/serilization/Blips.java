package com.example.asus1.remoteservice.serilization;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

class Blip1 implements Externalizable {

    public Blip1() {
        System.out.println("Bilp1 Constructor");

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Bilp1 writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Bilp1 readExternal");
    }
}


class Bilp2 implements Externalizable{


     Bilp2() {
        System.out.println("Bilp2 Constructor");

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Bilp2 writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Bilp2 readExternal");
    }
}

class Blip3 implements Externalizable{

    public int x;
    public int y;
    public String s;

    public Blip3(String s) {
        super();
        System.out.println("Bilp3(String s)");
        this.s = s;
    }

    public Blip3() {

        System.out.println("Bilp3 Default Constructor");
    }

    @Override
    public String toString() {
        System.out.println("toString");
        return "toString:  "+s+"---"+x+"--"+y;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip3 writeExternal");
        out.writeObject(this.x);
        out.writeObject(this.s);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip3 readExternal");
        this.x = (int)in.readObject();
        this.s = (String) in.readObject();


    }
}

public class Blips{

     public static void main(String[] args){
//         Blip1 b1 = new Blip1();
//         Bilp2 b2 = new Bilp2();

         Blip3 b3 = new Blip3("333333333");
         b3.x = 10;
         b3.y = 11;

         System.out.println(b3.toString());

         try {
             ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream("Blip3.out"));
            System.out.println("Saving objects");
             //outputStream.writeObject(b1);
             //outputStream.writeObject(b2);
             outputStream.writeObject(b3);
             outputStream.close();

             ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream("Blip3.out"));

             System.out.println("Recovering b3");
             b3 = (Blip3) inputStream.readObject();
             System.out.println(b3.toString());

         }catch (FileNotFoundException e){
             e.printStackTrace();

         }catch (IOException e){
             e.printStackTrace();

         }catch (ClassNotFoundException e){
             e.printStackTrace();

         }
     }

}
