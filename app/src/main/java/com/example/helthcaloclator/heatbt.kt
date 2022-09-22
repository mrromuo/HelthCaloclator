package com.example.helthcaloclator

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.get
import java.lang.NullPointerException

class heatbt : AppCompatActivity() {

    var Age:EditText? = null
    var RHR:EditText? = null
    var heartbeats:TextView? = null
    var heartbeats1:TextView? = null
    var heartbeats2:TextView? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    companion object{
        val  AGE_KEY = "userage"
        val HARTBEATS_KEY ="harts"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heatbt)
        Age = findViewById(R.id.HeartAgeEditText)
        RHR = findViewById(R.id.HeartRestingEditText)
        heartbeats = findViewById(R.id.HeartTextView)
        heartbeats1 = findViewById(R.id.HeartTextView1)
        heartbeats2 = findViewById(R.id.HeartTextView2)

        sharedPreferences = getSharedPreferences(BMI.DATABMI, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val getHart: String? = sharedPreferences.getString(HARTBEATS_KEY, null)
        val getAge:String? =sharedPreferences.getString(AGE_KEY,null)
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
                        editor.putString(AGE_KEY,Age?.text.toString())
                        editor.putString(HARTBEATS_KEY,RHR?.text.toString())
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

}