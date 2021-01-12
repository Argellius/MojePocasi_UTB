package com.example.mojepocasi_utb

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class Activity_historie : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_historie)
        supportActionBar?.hide()
        var listView = findViewById(R.id.listView_history) as ListView
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.data.observe(this, {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,  it.vyhledavaniHistorie.trim().split(';'))
            listView.adapter = adapter

        })


    }
}