package com.example.helthcaloclator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heatbt)
        Age = findViewById(R.id.HeartAgeEditText)
        RHR = findViewById(R.id.HeartRestingEditText)
        heartbeats = findViewById(R.id.HeartTextView)

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
                        val hMesssage:String =resources.getString(R.string.H_Target_min) + " $HRmin " + resources.getString(R.string.H_Target_mod) + " $HRMod " + resources.getString(R.string.H_Target_max) + " $HRintense "
                        heartbeats?.text = hMesssage
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