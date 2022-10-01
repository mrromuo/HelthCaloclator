package com.example.helthcaloclator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class HELP : AppCompatActivity(), AdapterView.OnItemSelectedListener {
      lateinit var webView: WebView
      lateinit var spinner: Spinner
      lateinit var webList:ArrayList<String>

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_help)
            spinner =findViewById(R.id.spinner)
            webView =findViewById(R.id.HelpWeb)

            webList = ArrayList()
            val localLang =MainActivity.loacallang
            loadwebSList(localLang)
            ArrayAdapter.createFromResource(
                  this,
                  R.array.helplist,
                  android.R.layout.simple_spinner_item
            ).also { adapter ->
                  // Specify the layout to use when the list of choices appears
                  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                  // Apply the adapter to the spinner
                  spinner.adapter = adapter
            }
            spinner.prompt ="Select Help Item"
            spinner.onItemSelectedListener = this
      }

      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
           Log.d("p2 ================= p2 is",p2.toString())
            val website :String= if (p2 <= webList.size) webList[p2] else webList[0]
            Log.d("website            ========            is",website)
            webView.settings.allowFileAccess = true
            webView.loadUrl(website)

      }

      override fun onNothingSelected(p0: AdapterView<*>?)
      {
            if (MainActivity.loacallang == "العربية")
            {
                  webView.loadUrl("file:///android_asset/ar_polcy.html")
            } else {
                  webView.loadUrl("file:///android_asset/polcy.html")
            }
      }

      fun loadwebSList(language:String){
            if (language == "العربية"){
                  webList.add("file:///android_asset/ar_bmi.html")
                  webList.add("file:///android_asset/ar_caloris.html")
                  webList.add("file:///android_asset/ar_heart.html")
                  webList.add("file:///android_asset/ar_water.html")
                  webList.add("file:///android_asset/ar_food.html")
                  webList.add("file:///android_asset/ar_polcy.html")
                  webList.add("file:///android_asset/ar_contactme.html")
            }else{
                  webList.add("file:///android_asset/bmi.html")
                  webList.add("file:///android_asset/caloris.html")
                  webList.add("file:///android_asset/heart.html")
                  webList.add("file:///android_asset/water.html")
                  webList.add("file:///android_asset/food.html")
                  webList.add("file:///android_asset/polcy.html")
                  webList.add("file:///android_asset/contactme.html")
            }
      }
}