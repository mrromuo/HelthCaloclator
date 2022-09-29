package com.example.helthcaloclator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView

class heatbt : AppCompatActivity() {

    var Age:EditText? = null
    var RHR:EditText? = null
    var heartbeats:TextView? = null
    var heartbeats1:TextView? = null
    var heartbeats2:TextView? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    companion object{
        const val  KEY_AGE = "userage"
        const val KEY_HEARTBEATS ="harts"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heatbt)
        setSupportActionBar(findViewById(R.id.Hearttoolbar))

        Age = findViewById(R.id.HeartAgeEditText)
        RHR = findViewById(R.id.HeartRestingEditText)
        heartbeats = findViewById(R.id.HeartTextView)
        heartbeats1 = findViewById(R.id.HeartTextView1)
        heartbeats2 = findViewById(R.id.HeartTextView2)

        sharedPreferences = getSharedPreferences(BMI.DATABMI, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val getHart: String? = sharedPreferences.getString(KEY_HEARTBEATS, null)
        val getAge:String? =sharedPreferences.getString(KEY_AGE,null)
        if (!getHart.isNullOrBlank()) RHR?.setText(getHart)
        if (!getAge.isNullOrBlank()) Age?.setText(getAge)
        Log.d("loade----------------------->","done as ${getAge} years ols and ${getHart} Heart beats")
    }

    fun heatCalculate(view: View) {


        if (!Age?.text.isNullOrBlank() ) {
            val hAge:Int = (Age?.text.toString()).toInt()
            if (!RHR?.text.isNullOrBlank()){
                val hresting:Int = (RHR?.text.toString()).toInt()
                val isAgeOK:Boolean = hAge in 15 .. 90
                val isHrestingOK:Boolean = hresting in 50..90
                if (isAgeOK){
                    if (isHrestingOK){
                        val MHR:Int = (220 - (hAge*0.7)).toInt()   // Maximum Heart Rate
                        val HRR: Int = MHR.minus(hresting)         // Heart Rate Reserve
                        val HRmin:Int = ((HRR*.5) + hresting).toInt()
                        val HRMod:Int = ((HRR*.7) + hresting).toInt()
                        val HRintense:Int = ((HRR*.85) + hresting).toInt()
                        editor.putString(KEY_AGE,Age?.text.toString())
                        editor.putString(KEY_HEARTBEATS,RHR?.text.toString())
                        editor.apply()
                        editor.commit()
                        Log.d("sheard----------------------->","done as ${Age?.text.toString()} years ols and ${RHR?.text.toString()} Heart beats")
                        val hMesssage:String =resources.getString(R.string.H_Target_min) + " $HRmin "
                        val hMesssage1:String =resources.getString(R.string.H_Target_mod) + " $HRMod "
                        val hMesssage2:String =resources.getString(R.string.H_Target_max) + " $HRintense "
                        heartbeats?.text = hMesssage
                        heartbeats1?.text = hMesssage1
                        heartbeats2?.text = hMesssage2
                    } else {
                        heartbeats?.text = resources.getString(R.string.H_RHR_Error)
                    }
                }else {
                    heartbeats?.text = resources.getString(R.string.H_Age_Error)
                }
            }else {
                heartbeats?.text = resources.getString(R.string.H_RHR_Error)
            }
        }else{
            heartbeats?.text = getText(R.string.H_Age_Error)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.heart_menu, menu)
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
            R.id.menu_food ->
            {
                Intent(this, Food::class.java)
            }
            else -> Intent(this, MainActivity2::class.java)
        }
        startActivity(intent)
        this.finish()
        return super.onOptionsItemSelected(item)
    }
}