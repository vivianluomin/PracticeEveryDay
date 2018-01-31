package com.example.asus1.learnkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
const  val  string : String = "ABC"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val s = describe("Hello")
        println(s)
        var  a = hasPrefix1(1)
        println(a)



    }


    fun hasPrefix(x: Any) = when(x) {
        is String -> x.startsWith("prefix")
        else -> false
    }

    fun hasPrefix1(x: Any) :Boolean{
        when(x) {
            is String -> return x.startsWith("prefix")
            else -> return false
        }

    }

    fun main(){
        val a:Int = 100
        println(a)

        val A :Int? = a
        val B :Int? = a

        println("A==B:${A==B}")
        println("A===B:${A===B}")

        var b = Array(3,{i->(i*2)})
        println(""+ b[0]+" "+b[1])


    }

    fun describe(obj: Any): String =
            when (obj) {
                1          -> "One"
                "Hello"    -> "Greeting"
                is Long    -> "Long"
                !is String -> "Not a string"
                else       -> "Unknown"
            }


}
