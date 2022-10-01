package com.example.helthcaloclator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.helthcaloclator.databinding.ActivityMainBinding

class BMI : AppCompatActivity() {

    private var height: EditText? = null
    private var weight: EditText? = null
    private var mImage:ImageView? = null
    private var fImage:ImageView? = null
    private var bmiview: TextView? = null
    private var progresbr: ProgressBar? = null
    private var classification: TextView? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    companion object {
        const val DATABMI = "mydata"
        const val KEY_WEIGHT = "BMIWeight"
        const val KEY_HEIGHT = "BMIHeight"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        setSupportActionBar(findViewById(R.id.bmitoolbar))

        height = findViewById(R.id.BMIheight)
        weight = findViewById(R.id.BMIweight)
        mImage = findViewById(R.id.maleimage)
        fImage = findViewById(R.id.femailImage)
        bmiview = findViewById(R.id.BMItv)
        progresbr = findViewById(R.id.determinate_progress_Bar)
        classification = findViewById(R.id.BMIclass)
        sharedPreferences = getSharedPreferences(DATABMI, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val getH: String? =
            sharedPreferences.getString(KEY_HEIGHT, null)
        val getW: String? =
            sharedPreferences.getString(KEY_WEIGHT, null)
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
            weightVal = if (weightVal < 30 || weightVal > 350) {
                1.0
            } else weightVal

            if (heightVal != .5 && weightVal != 1.0) {
                SaveData()
                val bmi = weightVal / heightVal
                bmiview?.text = String.format("BMI = %.2f", bmi)
                val BmiWord = BmiGagment(bmi)
                classification?.text = BmiWord
                val BmiMimage = getImage(bmi)
                mImage?.setImageResource(BmiMimage)
                val BmiFimage= getFImage(bmi)
                fImage?.setImageResource(BmiFimage)
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
    private fun SaveData(){
        // updating data
        editor.putString(KEY_WEIGHT, weight?.text.toString())
        editor.commit()
        editor.putString(KEY_HEIGHT, height?.text.toString())
        // editor.apply()
        editor.commit()
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

    private fun getImage(bmi: Double):Int
    {
        val imagnum = when (bmi)
        {
            in 0.0..18.49 -> R.drawable.under_weight_m
            in 18.50..24.99 -> R.drawable.normal_m
            in 25.0..29.99 -> R.drawable.over_wright_m
            in 30.0..34.99 -> R.drawable.obese_m
            in 35.0..40.0 -> R.drawable.extremly_obese_m
            else -> R.drawable.extremly_obese_m
        }
        return imagnum
    }
    private fun getFImage(bmi: Double): Int
    {
        val imagnum = when (bmi)
        {
            in 0.0..18.49 -> R.drawable.under_weight
            in 18.50..24.99 -> R.drawable.normal
            in 25.0..29.99 -> R.drawable.over_wright
            in 30.0..34.99 -> R.drawable.obese
            in 35.0..40.0 -> R.drawable.extremly_obese
            else -> R.drawable.extremly_obese
        }
        return imagnum
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bmi_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

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
            R.id.menu_help ->{
                help()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun foodx(view: View) {
        val intent = Intent(this, Food::class.java)
        startActivity(intent)
        this.finish()
    }
    private fun help() {
        val intent = Intent(this, HELP::class.java)
        startActivity(intent)
    }

    fun wtr() {
        val intent = Intent(this, water::class.java)
        startActivity(intent)
        this.finish()
    }
    fun hbt() {
        val intent = Intent(this, heatbt::class.java)
        startActivity(intent)
    }
    fun bmrx() {
        val intent = Intent(this, BMR::class.java)
        startActivity(intent)
        this.finish()
    }

    fun foodx() {
        val intent = Intent(this, Food::class.java)
        startActivity(intent)
        this.finish()
    }
}