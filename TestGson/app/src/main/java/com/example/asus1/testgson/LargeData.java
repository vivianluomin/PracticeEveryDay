package com.example.asus1.testgson;

/**
 * Created by asus1 on 2017/9/13.
 */

public class LargeData {

    private long[] numbers;
    public void create(final int length){
        numbers = new long[length];
        for (int i = 0;i < length ; i++){
            numbers[i] = i;
        }
    }

    public long[] getNumbers(){
        return numbers;
    }

}
