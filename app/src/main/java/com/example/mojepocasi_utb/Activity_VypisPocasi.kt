package com.example.mojepocasi_utb

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.json.JSONObject
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class Activity_VypisPocasi : AppCompatActivity() {
    var CITY: String = ""
    val API: String = "88a9f98d6e5762b928e5883964444849"
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__vypis_pocasi)
        supportActionBar?.hide()
        val myBtn = findViewById<Button>(R.id.button_najit)
        val poloha = findViewById<TextView>(R.id.editTextText_poloha)
        val poloha_textView = findViewById<TextView>(R.id.textView_poloha)
        val that = this;
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.firstName.observe(this, {
            poloha.text = it.firstName
            poloha.text = it.firstName
            this.CITY = it.firstName
            weatherTask().execute()
            Log.d("FirstName", it.firstName)
            Log.d("LastName", it.lastName)
            Log.d("AgeName", it.age.toString())
        })



        myBtn.setOnClickListener{
            mainViewModel.updateValue(poloha.text.toString(), "Jovanovic", 25)
            this.CITY = poloha.text.toString();
            findViewById<TextView>(R.id.textView_vitr_value).text = " "
            findViewById<TextView>(R.id.textView_min_value).text = " "
            findViewById<TextView>(R.id.textView_max_value).text = " "
            findViewById<TextView>(R.id.textView_pocasi_popis).text = " "
            findViewById<TextView>(R.id.textView_vychod_value).text = " "
            findViewById<TextView>(R.id.textView_zapad_value).text = " "
            weatherTask().execute()

        }

    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.nacitani).visibility = View.VISIBLE
            //findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.GONE
            findViewById<TextView>(R.id.ChybaText).visibility = View.GONE

        }
        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        override fun doInBackground(vararg p0: String?): String? {
            var response: String?

            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(Charsets.UTF_8)
                hideKeyboard()


            } catch (e: Exception) {
                response = null
                findViewById<TextView>(R.id.textView_teplota).text = "Chyba!"
                findViewById<TextView>(R.id.textView_poloha).text = "Neznámá poloha"

            }

            return response

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {

                var jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val vitr = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getString("temp") + "°C"
                val temp_min = main.getString("temp_min") + "°C"
                val temp_max = main.getString("temp_max") + "°C"
                val vychod: Long = sys.getLong("sunrise")
                val zapad: Long = sys.getLong("sunset")
                val vitrRychlost = vitr.getString("speed")
                val pocasiPopis = weather.getString("description")
                val adresa = jsonObj.getString("name") + ", " + sys.getString("country")
                val formatter: DateFormat = SimpleDateFormat("HH:mm:ss")
                formatter.setTimeZone(TimeZone.getTimeZone("CET"))

                findViewById<TextView>(R.id.textView_min_value).text = temp_min
                findViewById<TextView>(R.id.textView_max_value).text = temp_max
                findViewById<TextView>(R.id.textView_teplota).text = temp
                findViewById<TextView>(R.id.textView_poloha).text = adresa
                findViewById<TextView>(R.id.textView_pocasi_popis).text = pocasiPopis
                findViewById<TextView>(R.id.textView_vitr_value).text = vitrRychlost + " m/s"
                findViewById<TextView>(R.id.textView_vychod_value).text =
                    formatter.format(vychod * 1000)
                findViewById<TextView>(R.id.textView_zapad_value).text =
                    formatter.format(zapad * 1000)


                findViewById<ProgressBar>(R.id.nacitani).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.VISIBLE
            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.nacitani).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.VISIBLE
                findViewById<TextView>(R.id.ChybaText).visibility = View.VISIBLE
            }
        }

    }
}

