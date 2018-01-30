package com.example.asus1.learnkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
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


}
