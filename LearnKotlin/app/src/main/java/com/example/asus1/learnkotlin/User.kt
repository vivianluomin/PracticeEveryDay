package com.example.asus1.learnkotlin

/**
 * Created by asus1 on 2018/2/2.
 */

data class  User(val name: String = "",val age:Int = 0){


}

class Person(val name: String = "",val  age: Int = 0){
    private var mName = name
    get() {
        return field
    }
    set(value) {
        mName = field
    }

    private  var mAge = age
    get() {
        return field
    }
    set(value) {
        mAge = field
    }
}