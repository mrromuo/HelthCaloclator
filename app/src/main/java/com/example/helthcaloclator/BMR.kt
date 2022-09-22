package com.example.helthcaloclator

// حساب السعرات الحرارية
// metabolic rate
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.helthcaloclator.BMI.Companion.DATABMI

class BMR : AppCompatActivity() {
    private var agetext: EditText? = null
    private var heightText: EditText? = null
    private var weightText: EditText? = null
    private var sexRadioGroup: RadioGroup? = null
    private var activityRadioGroup: RadioGroup? = null
    private var BMRtvText: TextView? = null
    private var isSexMale: Boolean = true
    private var actyvity: Int = 0
    private var prebmr: Double? = 0.0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val MY_BMR_DATA ="bmrSharedPreferences"
    private val BMR_AGE_KEY = "AGE"
    private val BMR_GENDER_KEY ="SexKey"
    private val BMR_ACTYV_KEY ="ActivityKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr)
        agetext = findViewById(R.id.BMRage)
        heightText = findViewById(R.id.BMRheight)
        weightText = findViewById(R.id.BMRweight)
        sexRadioGroup = findViewById(R.id.BMRsexGroup)
        activityRadioGroup = findViewById(R.id.BMRactivityGroup)
        BMRtvText = findViewById(R.id.BMRtv)
        sharedPreferences = getSharedPreferences(DATABMI,Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        getMyData()
    }

    fun checksex(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.malebutton ->
                    if (checked) isSexMale = true

                R.id.femaleButton ->
                    if (checked) isSexMale = false
                else -> isSexMale = true
            }
            editor.putBoolean(BMR_GENDER_KEY, isSexMale)
            editor.commit()
        }
    }

    fun checkActivity(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.BMRsedntary -> if (checked) actyvity = 1
                R.id.BMRlightAct -> if (checked) actyvity = 2
                R.id.BMRmod -> if (checked) actyvity = 3
                R.id.BMRactiv -> if (checked) actyvity = 4
                R.id.BMRvaryActiv -> if (checked) actyvity = 5
                else -> actyvity = 0
            }
            editor.putInt(BMR_ACTYV_KEY,actyvity)
            editor.commit()
        }
    }

    fun BMRcalcolation(view: View) {
        var bmrx:Double = 0.0
        val age = if (agetext?.text.toString().isNotEmpty() && agetext?.text.toString()
                .isNotBlank()
        ) (agetext?.text.toString()).toInt() else 0
        var height: Double =
            if (heightText?.text.toString().isNotEmpty() && heightText?.text.toString()
                    .isNotBlank()
            ) (heightText?.text.toString()).toDouble() else 0.0
        if (height<2.5 && height != 0.0) {
            height *= 100.0
        }
        val weight: Double =
            if (weightText?.text.toString().isNotEmpty() && weightText?.text.toString()
                    .isNotBlank()
            ) (weightText?.text.toString()).toDouble() else 0.0
        if (age != 0 && height != 0.0 && weight != 0.0) {
            if (isSexMale == true) {
                prebmr = (weight * 10) + (height * 6.5) + (age * 5) + 5
                bmrx = when(actyvity) {
                    1 -> prebmr!! * 1.25
                    2 -> prebmr!! * 1.375
                    3 -> prebmr!! * 1.55
                    4 -> prebmr!! * 1.725
                    else -> 0.0
                }
            } else {
                prebmr = (weight * 10) + (height * 6.5) + (age * 5) - 161
                bmrx = when(actyvity) {
                    1 -> prebmr!! * 1.25
                    2 -> prebmr!! * 1.375
                    3 -> prebmr!! * 1.55
                    4 -> prebmr!! * 1.725
                    else -> 0.0
                }
            }
            BMRtvText?.text =String.format("= %.2f" , bmrx)
            savedata(agetext?.text.toString(),heightText?.text.toString(),weightText?.text.toString())
        } else {
            BMRtvText?.text = getText(R.string.BMRerror)
        }
    }

    private fun savedata(age:String, height:String, weight:String) {
        editor.putString(heatbt.AGE_KEY,age)
        editor.putString(BMI.HEIGHT_KEY,height)
        editor.putString(BMI.WEIGHT_KEY,weight)
        editor.apply()
        editor.commit()
    }
    private fun getMyData(){
        agetext?.setText(sharedPreferences.getString(heatbt.AGE_KEY,null))
        heightText?.setText(sharedPreferences.getString(BMI.HEIGHT_KEY,null))
        weightText?.setText(sharedPreferences.getString(BMI.WEIGHT_KEY,null))
        val gender = sharedPreferences.getBoolean(BMR_GENDER_KEY,true)
        isSexMale = gender
        actyvity = sharedPreferences.getInt(BMR_ACTYV_KEY,0)
        if (gender){
            sexRadioGroup?.check(R.id.malebutton)
        }else{
            sexRadioGroup?.check(R.id.femaleButton)
        }

        when(actyvity){
            1 -> activityRadioGroup?.check(R.id.BMRsedntary)
            2 -> activityRadioGroup?.check(R.id.BMRlightAct)
            3 -> activityRadioGroup?.check(R.id.BMRmod)
            4 -> activityRadioGroup?.check(R.id.BMRactiv)
            5 ->  activityRadioGroup?.check(R.id.BMRvaryActiv)
            else -> activityRadioGroup?.check(R.id.BMRsedntary)
        }
    }
}