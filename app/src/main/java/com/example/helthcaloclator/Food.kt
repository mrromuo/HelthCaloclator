package com.example.helthcaloclator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.helthcaloclator.KEYs.DATABMI
import com.example.helthcaloclator.KEYs.KEY_CALORIES
import com.example.helthcaloclator.KEYs.KEY_CARBOHYDRATE
import com.example.helthcaloclator.KEYs.KEY_FAT
import com.example.helthcaloclator.KEYs.KEY_PROTEIN

class Food : AppCompatActivity() {
      private lateinit var caloriesEdit:EditText
      private  var  calories:Double = 0.0
      private lateinit var seekBarPro: SeekBar
      private lateinit var seekBarProEditText: EditText
      private var ProtenPersentage = 30
      private lateinit var seekBarFat: SeekBar
      private lateinit var seekBarFatEditText: EditText
      private var FatPersentage = 20
      private lateinit var seekBarCrb:SeekBar
      private lateinit var seekBarCrbEditText: EditText
      private var CrbPersentage = 50
      private lateinit var resButton:Button
      private lateinit var Calculate:Button
      private lateinit var totalPercentages:TextView
      private var totalPercentageInt = 100
      private lateinit var carbohydratesHead:TextView
      private lateinit var carbohydratesViewresult:TextView
      private lateinit var fatHead:TextView
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
            resButton = findViewById(R.id.FoodResetting)
            totalPercentages = findViewById(R.id.totalPercentage)

            // Calories
            caloriesEdit =findViewById(R.id.FoodCaloriesEdit)
            Calculate = findViewById(R.id.Foodcalculate)


            // carbohydrates
            carbohydratesHead = findViewById(R.id.FoodcarpHead)
            carbohydratesViewresult = findViewById(R.id.FoodcarpTv)

            // Fats
            fatHead = findViewById(R.id.FoodFatHead)
            FatViewresult = findViewById(R.id.FoodFatTv)

            // Proteins
            ProteinHead = findViewById(R.id.FoodProHead)
            ProteinViewresult = findViewById(R.id.FoodProTv)

            // SEEKBARS AND THERE EDIT
            seekBarPro = findViewById(R.id.ProteinSeekBar)
            seekBarFat =findViewById(R.id.fatsSeekBar)
            seekBarCrb = findViewById(R.id.carbohydrateSeekBar)
            seekBarProEditText = findViewById(R.id.proteinPercentage)
            seekBarFatEditText = findViewById(R.id.fatPercentage)
            seekBarCrbEditText = findViewById(R.id.carbohydratePercentage)

            // Shared Preferences initiating
            sharedPreferences = getSharedPreferences(DATABMI, Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()

            // starting by get sharedPreferences data back
            getSharedPreferencesDataBack()

            // -------------------SEEKBARS LISTENERS------------------------------------
            // 1- Seekbar of Carbohydrate
            seekBarCrb.setOnSeekBarChangeListener(object :
                  SeekBar.OnSeekBarChangeListener {

                  override fun onProgressChanged(seek: SeekBar,
                                                 progress: Int, fromUser: Boolean) {
                        CrbPersentage = seekBarCrb.progress
                        seekBarCrbEditText.setText(CrbPersentage.toString())
                        setTotal()
                  }

                  override fun onStartTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is started
                  }

                  override fun onStopTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is stopped

                  }
            })

            // 2- Seekbar of FATs
            seekBarFat.setOnSeekBarChangeListener(object :
                  SeekBar.OnSeekBarChangeListener {

                  override fun onProgressChanged(seek: SeekBar,
                                                 progress: Int, fromUser: Boolean) {
                        FatPersentage =seekBarFat.progress
                        seekBarFatEditText.setText(FatPersentage.toString())
                        setTotal()
                  }

                  override fun onStartTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is started
                  }

                  override fun onStopTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is stopped

                  }
            })

            // 3- Seekbar of Proteins
            seekBarPro.setOnSeekBarChangeListener(object :
                  SeekBar.OnSeekBarChangeListener {

                  override fun onProgressChanged(seek: SeekBar,
                                                 progress: Int, fromUser: Boolean) {
                        ProtenPersentage =seekBarPro.progress
                        seekBarProEditText.setText(ProtenPersentage.toString())
                        setTotal()
                  }

                  override fun onStartTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is started
                  }

                  override fun onStopTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is stopped

                  }
            })
            seekBarCrbEditText.setOnClickListener(){
                  CrbPersentage = (seekBarCrbEditText.text).toString().toInt()
                  seekBarCrb.progress =CrbPersentage
                  setTotal()
            }
            seekBarFatEditText.setOnClickListener(){
                  FatPersentage =(seekBarFatEditText.text).toString().toInt()
                  seekBarFat.progress =FatPersentage
                  setTotal()
            }
            seekBarProEditText.setOnClickListener(){
                  ProtenPersentage = (seekBarProEditText.text).toString().toInt()
                  seekBarPro.progress = ProtenPersentage
                  setTotal()
            }
            // -------------------SEEKBARS LISTENERS END------------------------------------

           resButton.setOnClickListener(){
                 CrbPersentage = 50
                 seekBarCrb.progress = CrbPersentage
                 ProtenPersentage = 30
                 seekBarPro.progress = ProtenPersentage
                 FatPersentage = 20
                 seekBarFat.progress = FatPersentage
                 setTotal()
           }

            //  Result button program
            Calculate.setOnClickListener()
            {
                  if (!checkInputs())
                  {
                        telUserToCheckOut()
                  }
                  else if (readInputs())
                  {
                        viewResult()
                        saveData()
                  } else  telUserToCheckOut()
            }
      }

      private fun setTotal() {
            val colors = seekBarCrbEditText.textColors
            totalPercentageInt =FatPersentage+ProtenPersentage+CrbPersentage
            totalPercentages.text = totalPercentageInt.toString()
            if (totalPercentageInt>100 || totalPercentageInt <50) totalPercentages.setTextColor(getColor(R.color.RED))
                  else totalPercentages.setTextColor(colors)
      }

      private fun getSharedPreferencesDataBack() {
            caloriesEdit.setText(sharedPreferences.getString(KEY_CALORIES,null))
            CrbPersentage = sharedPreferences.getInt(KEY_CARBOHYDRATE,50)
            seekBarCrbEditText.setText(CrbPersentage.toString())
            seekBarCrb.progress =CrbPersentage
            ProtenPersentage = sharedPreferences.getInt(KEY_PROTEIN,30)
            seekBarProEditText.setText(ProtenPersentage.toString())
            seekBarPro.progress =ProtenPersentage
            FatPersentage = sharedPreferences.getInt(KEY_FAT,20)
            seekBarFatEditText.setText(FatPersentage.toString())
            seekBarFat.progress =FatPersentage
            setTotal()
      }

      private fun readInputs():Boolean {
            problemCode = 0
            calories = (caloriesEdit.text.toString()).toDouble()
            if (calories < 500 || calories>5000) problemCode = 1
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

      private fun saveData() {
            editor.putString(KEY_CALORIES,caloriesEdit.text.toString())
            editor.putInt(KEY_CARBOHYDRATE,CrbPersentage)
            editor.putInt(KEY_PROTEIN,ProtenPersentage)
            editor.putInt(KEY_FAT,FatPersentage)
            editor.apply()
            editor.commit()
      }

      private fun viewResult() {
           // As the human needs of  carb is 50% to 70% of his/hir needs of energy
            val CarbCal = calories*CrbPersentage/100
            val carbohydrates = CarbCal/4
            val  CarbohydratesdString = String.format(" %.2f",carbohydrates) + getString(R.string.unt_grams)

            // As the human needs of  protein is 30% to 35% of his/hir needs of energy
            val protCal =calories*ProtenPersentage/100
            val protein = protCal / 4
            val  ProteinString = String.format(" %.2f",protein) + getString(R.string.unt_grams)

            // As the human needs of  protein is 20% to 35% of his/hir needs of energy
            val FatCal =calories*FatPersentage/100
            val fat =FatCal / 8
            val  FatString = String.format(" %.2f",fat) + getString(R.string.unt_grams)

            // Viewing the Messages
            // 1. Carbohydrates
            carbohydratesHead.text = getString(R.string.food_carb_needs)
            carbohydratesViewresult.text = CarbohydratesdString
            //2. Proteins
            ProteinHead.text = getString(R.string.food_prot_needs)
            ProteinViewresult.text = ProteinString
            // 3. Fats
            fatHead.text = getString(R.string.food_fat_needs)
            FatViewresult.text = FatString
      }

      private fun checkInputs(): Boolean {
            problemCode = 0
            if (caloriesEdit.text.isNullOrBlank() || caloriesEdit.text.isEmpty()) problemCode = 1
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
                        Intent(this, Water::class.java)
                  }
                  R.id.menu_heart ->
                  {
                        Intent(this, HeartBeats::class.java)
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