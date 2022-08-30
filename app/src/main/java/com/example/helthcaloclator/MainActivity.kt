package com.example.helthcaloclator


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private  var imageView:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        imageView?.setImageResource(R.drawable.logo)
    }

    fun bmion(view: View){
        val intent= Intent(this,MainActivity2::class.java)
        startActivity(intent)
    }


}