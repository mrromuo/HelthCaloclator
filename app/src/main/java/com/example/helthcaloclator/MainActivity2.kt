package com.example.helthcaloclator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(findViewById(R.id.maintoolbar))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_help->{
                help()
                true
            }
            R.id.menu_BMI -> {
                bmi()
                true
            }
            R.id.menu_bmr -> {
                bmrx()
                true
            }
            R.id.menu_food ->{
                foodx()
                true
            }
            R.id.menu_water ->{
                wtr()
                true
            }
            R.id.menu_heart ->
            {
                hbt()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun help() {
        val intent = Intent(this, HELP::class.java)
        startActivity(intent)
    }


    fun bmi(view: View) {
        val intent = Intent(this, BMI::class.java)
        startActivity(intent)
    }
    fun wtr(view: View) {
        val intent = Intent(this, Water::class.java)
        startActivity(intent)
    }
    fun hbt(view: View) {
        val intent = Intent(this, HeartBeats::class.java)
        startActivity(intent)
    }
    fun bmrx(view: View) {
        val intent = Intent(this, BMR::class.java)
        startActivity(intent)

    }

    fun foodx(view: View) {
        val intent = Intent(this, Food::class.java)
        startActivity(intent)
    }
    fun bmi() {
        val intent = Intent(this, BMI::class.java)
        startActivity(intent)
    }
    fun wtr() {
        val intent = Intent(this, Water::class.java)
        startActivity(intent)
    }
    fun hbt() {
        val intent = Intent(this, HeartBeats::class.java)
        startActivity(intent)
    }
    fun bmrx() {
        val intent = Intent(this, BMR::class.java)
        startActivity(intent)

    }

    fun foodx() {
        val intent = Intent(this, Food::class.java)
        startActivity(intent)
    }
}