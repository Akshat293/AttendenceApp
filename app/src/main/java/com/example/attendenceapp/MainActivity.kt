package com.example.attendenceapp

import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.example.attendenceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    var mobile:String=""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}