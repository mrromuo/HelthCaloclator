package com.example.helthcaloclator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class Food : AppCompatActivity() {
      private lateinit var Calories:EditText
      private  var  calories:Double = 0.0
      private lateinit var Age:EditText
      private var age = 0
     private lateinit var Calculate:Button
      private lateinit var CarbohydratesHead:TextView
      private lateinit var CarbohydratesViewresult:TextView
      private lateinit var FatHead:TextView
      private lateinit var FatViewresult:TextView
      private lateinit var ProteinHead:TextView
      private lateinit var ProteinViewresult:TextView
      private var problemCode =0
      private lateinit var sharedPreferences: SharedPreferences
      private lateinit var editor: SharedPreferences.Editor

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_food)
            setSupportActionBar(findViewById(R.id.foodtoolbar))

            // inflating my UI Components
            // Calories
            Calories =findViewById(R.id.FoodCaloriesEdit)
            Calculate = findViewById(R.id.Foodcalculate)
            // Age
            Age = findViewById(R.id.FoodAgeEdit)
            // carbohydrates
            CarbohydratesHead = findViewById(R.id.FoodcarpHead)
            CarbohydratesViewresult = findViewById(R.id.FoodcarpTv)
            // Fats
            FatHead = findViewById(R.id.FoodFatHead)
            FatViewresult = findViewById(R.id.FoodFatTv)
            // Proteins
            ProteinHead = findViewById(R.id.FoodProHead)
            ProteinViewresult = findViewById(R.id.FoodProTv)

            // Shared Preferences initiating
            ProteinViewresult = findViewById(R.id.FoodProTv)
            sharedPreferences = getSharedPreferences(BMI.DATABMI, Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()

            // starting by get sharedPreferences data back
            Calories.setText(sharedPreferences.getString(BMR.KEY_CALORIES,null))
            Age.setText(sharedPreferences.getString(heatbt.KEY_AGE,null))

            //  Result button program
            Calculate.setOnClickListener()
            {
                  if (!checkInputs())
                  {
                        telUserToCheckOut()
                  }
                  else if (readInputs())
                  {
                        viewRsult()
                        savedata()
                  } else  telUserToCheckOut()
            }
      }

      private fun readInputs():Boolean {
            problemCode = 0
            calories = (Calories.text.toString()).toDouble()
            if (calories < 500 || calories>5000) problemCode = 1
            age = (Age.text.toString()).toInt()
            if (age <3 || age >90) problemCode += 10
            return problemCode ==0
      }

      private fun telUserToCheckOut() {
            // Building a text message of the Error
            val textm:String =when(problemCode)
            {
                  1 -> getString(R.string.foodcalorisx)
                  10->getString(R.string.H_Age_Error)
                  else->getString(R.string.BMRerror)
            }

            // Alert Dialog to tell the user what is the problem
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.BMRrsultTitleerorr)
                  .setMessage(textm)
                  .setNegativeButton(android.R.string.ok)
                  {
                        a,v ->
                  }
                  .setIcon(android.R.drawable.ic_dialog_alert)
                  .show()
      }

      private fun savedata() {
            editor.putString(BMR.KEY_CALORIES,Calories.text.toString())
            editor.apply()
            editor.commit()
      }

      private fun viewRsult() {
           // As the human needs of  carb is 50% to 70% of his/hir needs of energy
            val CarbCal = calories*0.50
            val carbohydrates = CarbCal/4
            val  CarbohydratesdString = String.format(" %.2f",carbohydrates) + getString(R.string.unt_grams)

            // As the human needs of  protein is 30% to 35% of his/hir needs of energy
            val protCal = when(age)
            {
                  in 4..18 -> calories*0.3
                  in 19 .. 90 -> calories*0.35
                  else ->calories*0.2
            }
            val protein = protCal / 4
            val  ProteinString = String.format(" %.2f",protein) + getString(R.string.unt_grams)

            // As the human needs of  protein is 20% to 35% of his/hir needs of energy
            val FatCal =when(age)
            {
                  in 4..18 -> calories*0.25
                  in 19 .. 90 -> calories*0.20
                  else ->calories*0.4
            }
            val fat =FatCal / 8
            val  FatString = String.format(" %.2f",fat) + getString(R.string.unt_grams)

            // Viewing the Messages
            // 1. Carbohydrates
            CarbohydratesHead.text = getString(R.string.food_carb_needs)
            CarbohydratesViewresult.text = CarbohydratesdString
            //2. Proteins
            ProteinHead.text = getString(R.string.food_prot_needs)
            ProteinViewresult.text = ProteinString
            // 3. Fats
            FatHead.text = getString(R.string.food_fat_needs)
            FatViewresult.text = FatString
      }

      private fun checkInputs(): Boolean {
            problemCode = 0
            if (Calories.text.isNullOrBlank() || Calories.text.isEmpty()) problemCode = 1
            if (Age.text.isNullOrBlank() || Age.text.isEmpty()) problemCode += 10
            return problemCode == 0
      }
      override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.food_menu, menu)
            return true
      }
      override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle item selection
            val intent = when (item.itemId){
                  R.id.menu_BMI -> {
                        Intent(this, BMI::class.java)
                  }
                  R.id.menu_bmr ->{
                        Intent(this, BMR::class.java)
                  }
                  R.id.menu_water ->{
                        Intent(this, water::class.java)
                  }
                  R.id.menu_heart ->
                  {
                        Intent(this, heatbt::class.java)
                  }
                  else -> Intent(this, MainActivity2::class.java)
            }
            startActivity(intent)
            this.finish()
            return super.onOptionsItemSelected(item)
      }

}