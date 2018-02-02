package com.example.asus1.learnkotlin

/**
 * Created by asus1 on 2018/1/31.
 */

interface MyInterface{
    val prop:Int

    val propertyWithImplementation:String
    get() = "foo"

}


 class MyTest{

   var subject:TestSubject

    constructor(subject : TestSubject){
        this.subject = subject
    }

}

open class Outer{
    private var  a = 1
    protected open  val b = 2
    internal  val c = 3
    val d = 4//默认public

    protected class  Nested{
        public  val  e :Int = 5
    }

    fun setA(a:Int){
        this.a = a
    }


    val ll = mutableListOf<Int>(1,2,3)



}


class  Subclass:Outer(){
    //a不可见
    //bcd可见
    //Nested和e可见
    override val b = 5




}

class Unrelated{

    val o :Outer = Outer()



}

class D{
    fun bar(){
        println("D:bar")
    }
}

class C{

    fun baz(){
        println("C:baz")
    }

   fun  D.foo(){
       bar()
       baz()
   }

    fun caller(d:D){
        d.foo()
    }
}







