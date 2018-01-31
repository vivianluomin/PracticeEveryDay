package com.example.asus1.learnkotlin

/**
 * Created by asus1 on 2018/1/31.
 */

interface MyInterface{
    val prop:Int

    val propertyWithImplementation:String
    get() = "foo"

}

public class MyTest{

   var subject:TestSubject

    constructor(subject : TestSubject){
        this.subject = subject
    }

}