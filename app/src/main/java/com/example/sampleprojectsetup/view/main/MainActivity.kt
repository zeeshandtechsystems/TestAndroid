package com.example.sampleprojectsetup.view.main

import android.os.Bundle
import com.example.sampleprojectsetup.R
import com.example.sampleprojectsetup.view.base.ActivityBase

class MainActivity : ActivityBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}