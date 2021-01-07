package com.example.mojepocasi_utb

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Activity_VypisPocasi : AppCompatActivity() {
    var CITY: String = ""
    val API: String = "88a9f98d6e5762b928e5883964444849"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__vypis_pocasi)
        supportActionBar?.hide()
        val myBtn = findViewById<Button>(R.id.button_najit)
        val poloha = findViewById<TextView>(R.id.editTextText_poloha)
        myBtn.setOnClickListener{
            this.CITY = poloha.text.toString();
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

        override fun doInBackground(vararg p0: String?): String? {
            var response: String?

            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(Charsets.UTF_8)
            } catch (e: Exception) {
                response = null
                findViewById<TextView>(R.id.textView_teplota).text = "Chyba!"
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

