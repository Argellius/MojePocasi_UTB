package com.example.mojepocasi_utb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class Activity_historie : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_historie)
        supportActionBar?.hide()
        var listView = findViewById(R.id.listView_history) as ListView
        var vyhledavaniHistorie : List<String> = listOf();
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.data.observe(this, {
            vyhledavaniHistorie = it.vyhledavaniHistorie.trim().split(';')
            var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,  vyhledavaniHistorie)
            listView.adapter = adapter

        })


        listView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, Activity_VypisPocasi::class.java)
            intent.putExtra("ClickedValuelistViewItem", vyhledavaniHistorie[position].toString() )
            startActivity(intent)
        }


    }
}