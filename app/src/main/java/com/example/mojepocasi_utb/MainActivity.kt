package com.example.mojepocasi_utb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val btnVyhledat = findViewById(R.id.button_pocasi) as Button
        btnVyhledat.setOnClickListener {
            val intent = Intent(this, Activity_VypisPocasi::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        val btnHistorie = findViewById(R.id.button_Historie) as Button
        btnHistorie.setOnClickListener {
            //Historie
        }



        val btnKonec = findViewById(R.id.button_konec) as Button
        btnKonec.setOnClickListener {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            super.finish()
        }
    }
}