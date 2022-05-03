package com.example.attendenceapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.attendenceapp.R
import com.example.attendenceapp.databinding.FragmentChooseNameBinding


class ChooseNameFragment : Fragment() {

private lateinit var binding: FragmentChooseNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_choose_name,container,false)
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getActivity()?.finish();
            }
        })
        binding.hiri.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_chooseBatchFragment_to_hiriFragment)
        }
        binding.back.setOnClickListener {
            getActivity()?.finish();
        }
        return binding.root

    }


}