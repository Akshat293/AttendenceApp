package com.example.attendenceapp.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.attendenceapp.R
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


class SplashScreenFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        Log.d("main",currentDate.toString())
        Handler(Looper.getMainLooper()).postDelayed({
        val currentUser = mAuth!!.currentUser
        if(currentUser!=null){
            view?.findNavController()?.navigate(R.id.action_splashScreenFragment_to_chooseBatchFragment)

        }else{
            view?.findNavController()?.navigate(R.id.action_splashScreenFragment_to_enterphoneFragment)

        }
        }, 1000)
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}