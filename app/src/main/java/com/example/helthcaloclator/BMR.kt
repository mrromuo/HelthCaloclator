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
import androidx.appcompat.app.AlertDialog
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

    companion object
    {
        const val KEY_GENDER ="SexKey"
        const val KEY_ACTIVITY ="ActivityKey"
        const val KEY_CALORIES ="Calories"
    }

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
            editor.putBoolean(KEY_GENDER, isSexMale)
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
            editor.putInt(KEY_ACTIVITY,actyvity)
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
            BMRtvText?.text =String.format("%.2f" , bmrx)
            val title:String =getText(R.string.BMRrsultTitle).toString()
            val msg = String.format(" %.2f" , bmrx)
            mydaialog(title,msg)
            savedata(agetext?.text.toString(),heightText?.text.toString(),weightText?.text.toString(),bmrx.toString())
        } else {
            BMRtvText?.text = getText(R.string.BMRerror)
            val title:String =getText(R.string.BMRrsultTitleerorr).toString()
            val msg = getText(R.string.BMRerror).toString()
            mydaialog(title,msg)
        }
    }

    private fun savedata(age:String, height:String, weight:String,Calories:String) {
        editor.putString(heatbt.KEY_AGE,age)
        editor.putString(BMI.KEY_HEIGHT,height)
        editor.putString(BMI.KEY_WEIGHT,weight)
        editor.putString(KEY_CALORIES,Calories)
        editor.apply()
        editor.commit()
    }

    private fun getMyData(){
        agetext?.setText(sharedPreferences.getString(heatbt.KEY_AGE,null))
        heightText?.setText(sharedPreferences.getString(BMI.KEY_HEIGHT,null))
        weightText?.setText(sharedPreferences.getString(BMI.KEY_WEIGHT,null))
        val gender = sharedPreferences.getBoolean(KEY_GENDER,true)
        isSexMale = gender
        actyvity = sharedPreferences.getInt(KEY_ACTIVITY,0)
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

    private fun mydaialog(title:String,Mssage:String){
        val blder = AlertDialog.Builder(this)
        blder.setTitle(title)
            .setMessage(Mssage)
            .setPositiveButton(android.R.string.ok){a,b->
            }
            .show()
    }
}