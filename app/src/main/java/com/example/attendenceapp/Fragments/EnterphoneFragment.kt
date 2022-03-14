package com.example.attendenceapp.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.attendenceapp.R
import com.example.attendenceapp.databinding.FragmentEnterphoneBinding


class EnterphoneFragment : Fragment() {
  private lateinit var binding:FragmentEnterphoneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_enterphone,container,false)
        binding.editNumber.addTextChangedListener(watcher)
        binding.verifyButton.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            binding.apply {
                if(isValidMobile(editNumber.text.toString().trim())){
                    val bundle=Bundle().apply {
                   putString("mobile",editNumber.text.toString().trim())
                        }
                    view?.findNavController()?.navigate(R.id.action_enterphoneFragment_to_otpVerifyFragment2,bundle)
                }else{
                    binding.editNumber.startAnimation(shakeError())
                }


            }
        }
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getActivity()?.finish();
            }
        })
        return binding.root
    }

    // Animation:-
    fun shakeError(): TranslateAnimation? {
        val shake = TranslateAnimation(0F, 10F, 0F, 0F)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7F)
        return shake
    }
    private fun hideSoftKeyboard() {
        if (activity?.currentFocus != null) {
            val inputMethodManager = activity?.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(activity?.currentFocus!!.windowToken, 0)
        }
    }
    fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }
    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        @SuppressLint("ResourceAsColor")
        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (editNumber.text.toString().trim().length==10) {
                    Log.d("main","aajafinal")
                    verifyButton.setBackgroundResource(R.drawable.active_button)
                    verifyButton.setEnabled(true)

                } else
                    verifyButton.setEnabled(false)
                Log.d("main","aaja")
                verifyButton.setBackgroundResource(R.drawable.inactive_button)
                }
            }
        }
    }

