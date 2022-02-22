package com.eldho.sampleproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getUserData()


        viewModel.getUserObservable().observe(this@MainActivity){

            var emailList = ""
            emailList = "\n"+emailList+ it.data?.email

            println(emailList)
        }

    }
}