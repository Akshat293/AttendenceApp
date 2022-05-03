package com.example.attendenceapp.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.attendenceapp.R
import com.example.attendenceapp.databinding.FragmentOtpVerifyBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class OtpVerifyFragment : Fragment() {
private lateinit var binding:FragmentOtpVerifyBinding
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var auth : FirebaseAuth
    lateinit var verificationCode:String
    lateinit var phoneNumber:String
    lateinit var otp:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_otp_verify,container,false)
        lifecycleScope.launch {
            binding.progressBar.visibility=View.VISIBLE
            StartFirebaseLogin()
        }

        binding.apply {
            inputotp1.addTextChangedListener(textWatcherOtp)
            inputotp2.addTextChangedListener(textWatcherOtp)
            inputotp3.addTextChangedListener(textWatcherOtp)
            inputotp4.addTextChangedListener(textWatcherOtp)
            inputotp5.addTextChangedListener(textWatcherOtp)
            inputotp6.addTextChangedListener(textWatcherOtp)

        }


        automove()
        val bundle = this.arguments
             if (bundle != null) {

                phoneNumber = bundle.getString("mobile").toString()
                 binding.otpPhoneNumber.text=phoneNumber
                 PhoneAuthProvider.getInstance().verifyPhoneNumber(
                     "+91"+phoneNumber,
                     60,
                     TimeUnit.SECONDS,
                     requireActivity(),
                     mCallbacks
                 )
             }

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getActivity()?.finish();
            }
        })



        return binding.root

    }

    private fun StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance()
        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Toast.makeText(requireContext(), "verification completed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(requireContext(), "verification fialed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationCode = s
                Toast.makeText(requireContext(), "Code sent", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.INVISIBLE

            }
        }
    }
    private fun automove() {
        binding.apply {
            inputotp1.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) //size as per your requirement
                    {
                        inputotp2.requestFocus()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                }
            })
            inputotp2.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) //size as per your requirement
                    {
                        inputotp3.requestFocus()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {

                }
            })
            inputotp3.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                    if (s.toString().trim().isNotEmpty()) //size as per your requirement
                    {
                        inputotp4.requestFocus()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {

                }

                override fun afterTextChanged(s: Editable) {

                }
            })
            inputotp4.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) //size as per your requirement
                    {
                        inputotp5.requestFocus()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {

                }

                override fun afterTextChanged(s: Editable) {

                }
            })
            inputotp5.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) //size as per your requirement
                    {
                        inputotp6.requestFocus()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                }
            })
        }
    }
    private val textWatcherOtp: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if ( inputotp1.text.length == 1 &&  inputotp2.text.length == 1 &&  inputotp3.text.length == 1 &&  inputotp4.text.length == 1 &&  inputotp5.text.length == 1 &&  inputotp6.text.length == 1) {
                    val otpCombined: String =
                        inputotp1.text.toString() + inputotp2.text
                            .toString() +
                                inputotp3.text.toString() +  inputotp4.text
                            .toString() +
                                inputotp5.text.toString() +  inputotp6.text
                            .toString()
                    val credential = PhoneAuthProvider.getCredential(verificationCode, otpCombined)
                    verifyButton.setBackgroundResource(R.drawable.active_button)
                    lifecycleScope.launch {
                        binding.progressBar.visibility=View.VISIBLE
                        SigninWithPhone(credential)
                    }

                }
            }
        }
    }
    private fun SigninWithPhone(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.progressBar.visibility=View.INVISIBLE
                   view?.findNavController()?.navigate(R.id.action_otpVerifyFragment2_to_chooseBatchFragment)
                } else {
                    Toast.makeText(requireContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility=View.INVISIBLE
                    binding.apply {
                        inputotp1.startAnimation(shakeError())
                        inputotp2.startAnimation(shakeError())
                        inputotp3.startAnimation(shakeError())
                        inputotp4.startAnimation(shakeError())
                        inputotp5.startAnimation(shakeError())
                        inputotp6.startAnimation(shakeError())
                    }


                }
            }
    }
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
}