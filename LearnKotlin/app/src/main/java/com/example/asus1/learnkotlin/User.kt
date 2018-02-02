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

    val ab:A = object :A(1),B{
        override fun b() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override val y: Int = 15
    }
}

open class A(x:Int){
    public  open  val y:Int = x
}

interface B{
    fun b()
}

interface Fatctory<T>{
    fun  create():T
}

class MyClass{
    companion object :Fatctory<MyClass>{
        override fun create(): MyClass {

           return MyClass()
        }
    }
}

interface Base{
    fun print()
}

class BaseImpl(val x: Int):Base{
    override fun print() {
        print(x)
    }
}

class Derived(b:Base):Base by b

fun main(args:Array<String>){
    val b = BaseImpl(10)
    Derived(b).print()
}

