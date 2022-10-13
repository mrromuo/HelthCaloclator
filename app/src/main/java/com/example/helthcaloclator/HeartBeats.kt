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
import com.example.helthcaloclator.KEYs.DATABMI
import com.example.helthcaloclator.KEYs.KEY_AGE
import com.example.helthcaloclator.KEYs.KEY_HEARTBEATS

class HeartBeats : AppCompatActivity() {

    private var ageEditText:EditText? = null
    private var hrhEditText:EditText? = null
    private var heartbeats:TextView? = null
    private var heartbeats1:TextView? = null
    private var heartbeats2:TextView? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heatbt)
        setSupportActionBar(findViewById(R.id.Hearttoolbar))

        ageEditText = findViewById(R.id.HeartAgeEditText)
        hrhEditText = findViewById(R.id.HeartRestingEditText)
        heartbeats = findViewById(R.id.HeartTextView)
        heartbeats1 = findViewById(R.id.HeartTextView1)
        heartbeats2 = findViewById(R.id.HeartTextView2)

        sharedPreferences = getSharedPreferences(DATABMI, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val getHart: String? = sharedPreferences.getString(KEY_HEARTBEATS, null)
        val getAge:String? =sharedPreferences.getString(KEY_AGE,null)
        if (!getHart.isNullOrBlank()) hrhEditText?.setText(getHart)
        if (!getAge.isNullOrBlank()) ageEditText?.setText(getAge)
    }

    fun heatCalculate(view: View) {
        if (!ageEditText?.text.isNullOrBlank() ) {
            val hAge:Int = (ageEditText?.text.toString()).toInt()
            if (!hrhEditText?.text.isNullOrBlank()){
                val hresting:Int = (hrhEditText?.text.toString()).toInt()
                val isAgeOK:Boolean = hAge in 15 .. 90
                val isHrestingOK:Boolean = hresting in 50..90
                if (isAgeOK){
                    if (isHrestingOK){
                        val mhr:Int = (220 - (hAge*0.7)).toInt()   // Maximum Heart Rate
                        val hrr: Int = mhr.minus(hresting)         // Heart Rate Reserve
                        val hrMin:Int = ((hrr*.5) + hresting).toInt()
                        val hrMod:Int = ((hrr*.7) + hresting).toInt()
                        val hrIintense:Int = ((hrr*.85) + hresting).toInt()
                        saveData()
                        val hMesssage:String =resources.getString(R.string.H_Target_min) + " $hrMin "
                        val hMesssage1:String =resources.getString(R.string.H_Target_mod) + " $hrMod "
                        val hMesssage2:String =resources.getString(R.string.H_Target_max) + " $hrIintense "
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
private fun saveData(){
    editor.putString(KEY_AGE,ageEditText?.text.toString())
    editor.putString(KEY_HEARTBEATS,hrhEditText?.text.toString())
    editor.apply()
    editor.commit()
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
                Intent(this, Water::class.java)
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