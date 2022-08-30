package com.example.helthcaloclator

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

class BMI : AppCompatActivity() {

    private var height: EditText? = null
    private var weight: EditText? = null
    private var bmiview: TextView? = null
    private var progresbr: ProgressBar? = null
    private var classification: TextView? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        val DATABMI = "BMIdata"
        val WEIGHT_KEY = "BMIWeight"
        val HEIGHT_KEY = "BMIHeight"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        height = findViewById(R.id.BMIheight)
        weight = findViewById(R.id.BMIweight)
        bmiview = findViewById(R.id.BMItv)
        progresbr = findViewById(R.id.determinate_progress_Bar)
        classification = findViewById(R.id.BMIclass)
        sharedPreferences = getSharedPreferences(DATABMI, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val getH: String? =
            sharedPreferences.getString(HEIGHT_KEY, null)
        val getW: String? =
            sharedPreferences.getString(WEIGHT_KEY, null)
        height?.setText(getH)
        weight?.setText(getW)

    }

    fun bmiCalculate(view: View) {
        if (height?.text.isNullOrBlank()) {
            bmiview?.text = getText(R.string.height_Erorr)
        } else if (weight?.text.isNullOrBlank()) {
            bmiview?.text = getText(R.string.weight_Erorr)
        } else {
            var heightVal: Double = (height?.text.toString()).toDouble()
            var weightVal: Double = (weight?.text.toString()).toDouble()

            heightVal = when (heightVal) {
                in 1.0..2.50 -> heightVal * heightVal
                in 100.0..250.0 -> (heightVal * heightVal) / 10000
                else -> .5
            }
            weightVal = if (weightVal < 40 || weightVal > 400) {
                1.0
            } else weightVal

            if (heightVal != .5 && weightVal != 1.0) {
                // updating data
                editor.putString(WEIGHT_KEY, weight?.text.toString())
                editor.commit()
                editor.putString(HEIGHT_KEY, height?.text.toString())
               // editor.apply()
                editor.commit()

                val bmi = weightVal / heightVal
                bmiview?.text = String.format("BMI = %.2f", bmi)
                val BmiWord = BmiGagment(bmi)
                classification?.text = BmiWord
                val progress = (bmi * 2).toInt()
                progresbr?.progress = if (progress < 100) {
                    progress
                } else 100
                progresbr?.progressTintList = if (progress < 36 || progress > 80) {
                    ColorStateList.valueOf(Color.RED)
                } else if (progress < 52) {
                    ColorStateList.valueOf(Color.GREEN)
                } else if (progress < 72) {
                    ColorStateList.valueOf(Color.BLUE)
                } else {
                    ColorStateList.valueOf(Color.YELLOW)
                }
            } else if (heightVal == .5) {
                bmiview?.text = getText(R.string.height_Erorr)
            } else {
                bmiview?.text = getText(R.string.weight_Erorr)
            }
        }
    }

    fun BmiGagment(bmi: Double): String {
        val r = when (bmi) {
            in 0.0..18.49 -> resources.getString(R.string.weight1)
            in 18.50..24.99 -> resources.getString(R.string.weight2)
            in 25.0..29.99 -> resources.getString(R.string.weight3)
            in 30.0..34.99 -> resources.getString(R.string.weight4)
            in 35.0..40.0 -> resources.getString(R.string.weight5)
            else -> resources.getString(R.string.weight6)
        }
        return r
    }

}