package com.example.annotationprocessor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catList.adapter = CatAdapter(
            listOf(
                Cat("Boris", "5 years"),
                Cat("Midi", "13 years")
            )
        )
    }
}