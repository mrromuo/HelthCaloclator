package com.example.helthcaloclator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
    fun bmi(view: View) {
        val intent = Intent(this, BMI::class.java)
        startActivity(intent)
    }
    fun wtr(view: View) {
        val intent = Intent(this, water::class.java)
        startActivity(intent)
    }
    fun hbt(view: View) {
        val intent = Intent(this, heatbt::class.java)
        startActivity(intent)
    }
    fun bmrx(view: View) {
        val intent = Intent(this, BMR::class.java)
        startActivity(intent)

    }
}