package com.example.attendenceapp.Fragments

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.example.attendenceapp.MainActivity
import com.example.attendenceapp.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap



class AttendanceFragment : Fragment() {
     var SubCode:String=""
    var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var user: MutableMap<String?, Any?>? = HashMap()
        val bundle = this.arguments
        if (bundle != null) {
            SubCode= bundle.getString("FCOMM").toString()
        }
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        if (user != null) {
            user.put(currentDate.toString(),"Present")
        }
        if (user != null) {
            db.collection("Dr Hrishikesh Venkataraman").document("FCOMMUG2").collection("S20200020243")
                .document(currentDate.toString()).set(user)

        }

        return inflater.inflate(R.layout.fragment_attendance, container, false)
    }




    }



