package com.example.helthcaloclator


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.helthcaloclator.BMI.Companion.DATABMI
import com.example.helthcaloclator.BMI.Companion.KEY_WEIGHT


class Water : AppCompatActivity() {

    private var bodyWeight: EditText? = null
    private var waterNeeds: TextView? = null
    private  var waterNeeds2: TextView? = null
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)
        setSupportActionBar(findViewById(R.id.watertoolbar))

        bodyWeight = findViewById(R.id.W_bodyweight)
        waterNeeds = findViewById(R.id.WTRtv)
        waterNeeds2 = findViewById(R.id.WTRtv2)
        sharedPreferences = this.getSharedPreferences(DATABMI,Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val weightData:String? = sharedPreferences.getString(KEY_WEIGHT,null)
        if(!weightData.isNullOrBlank()) {
            bodyWeight?.setText(weightData)
        }
    }

    fun watercalculation(view: View) {

        if (!bodyWeight?.text.isNullOrBlank()) {
            val weight = (bodyWeight?.text.toString()).toDouble()
            val highWaterNeeds = (weight * 29.5 * 2)/1000
            val HighWN = String.format("L %.2f" ,highWaterNeeds)
            val Hcup = (highWaterNeeds / .25).toInt()
            val lowWaterNeeds = (weight * 14.8 * 2)/1000
            val LowWN = String.format("L %.2f" ,lowWaterNeeds)
            val Lcup = (lowWaterNeeds / .25).toInt()
            val fr = getText(R.string.from)
            val to = getText(R.string.to)
            val ML = getText(R.string.ml)
            val cups = getText(R.string.cups)
            val myTexts = "$fr $LowWN $to $HighWN $ML "
            val myTexts2 = "$fr $Lcup $to $Hcup $cups"
            waterNeeds?.text = myTexts
            waterNeeds2?.text = myTexts2
            editor.putString(KEY_WEIGHT,bodyWeight?.text.toString())
            editor.apply()
            editor.commit()
        } else waterNeeds?.text = getText(R.string.W_weightError)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.water_menu, menu)
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
            R.id.menu_heart ->{
                Intent(this, HeartBeats::class.java)
            }
            R.id.menu_food ->
            {
                Intent(this, Food::class.java)
            }
            R.id.menu_help ->{
                Intent(this, HELP::class.java)
            }
            else -> Intent(this, MainActivity2::class.java)
        }
        startActivity(intent)
        this.finish()
        return super.onOptionsItemSelected(item)
    }
}
