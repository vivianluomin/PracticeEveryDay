package com.example.asus1.learnkotlin

import android.net.wifi.p2p.WifiP2pManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.util.*

const  val  string : String = "ABC"

class MainActivity : AppCompatActivity() {

    fun MutableList<Int>.swap(index1:Int,index2:Int){
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }


    var string:String = ""
    get() = this.toString()
    set(value) {
        if (field!=("")){
            field = value
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val s = describe("Hello")
//        println(s)
//        var  a = hasPrefix1(1)
//        println(a)

//        var c = C()
//        c.foo()
//        c.foo(1)

//        val list = mutableListOf<Int>(1,2,3)
//        list.swap(0,2)

        var mTextView = findViewById<TextView>(R.id.tv_textview)

//        mTextView.setOnClickListener { view: View? -> println("click") }
//
//
//
//        var user = User("vivian",21)
//        var (name,age) = user
//        println("user's name is $name and age is $age")
//      user =   user.copy(age = 22)
//        var (name1,age1) = user
//        println("user's name is $name1 and age is $age1")
//
//        var person  = Person("vivian2",18)
//        println("person's name is ${person.name},age is ${person.age}")



        val lazyValue:String by lazy {
            println("computed!")
            "Hello"
        }

        println(lazyValue)
        println(lazyValue)



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
