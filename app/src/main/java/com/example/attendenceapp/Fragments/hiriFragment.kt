package com.example.attendenceapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.attendenceapp.MainActivity2
import com.example.attendenceapp.R
import com.example.attendenceapp.databinding.FragmentHiriBinding


class hiriFragment : Fragment() {
private lateinit var binding:FragmentHiriBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hiri,container,false)
        binding.fcom.setOnClickListener {
            val bundle=Bundle().apply {
                putString("SubCode","FCOMM")
            }

            val intent = Intent(activity, MainActivity2::class.java)
            startActivity(intent)

        }
        return binding.root
    }


}