package com.example.e_commerce.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.example.e_commerce.models.auth.login.LoginRequest
import com.example.e_commerce.util.Constants.Companion.EMPTY
import com.example.e_commerce.util.Constants.Companion.SHARED_PREFERENCES_NAME
import com.example.e_commerce.util.Constants.Companion.TOKEN
import com.example.e_commerce.util.Resource


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    lateinit var viewModel: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        viewModel.login_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    try {

                        Log.e("hello", response.massage.toString())
                        Log.e("hello Success", "Success${response.data?.data?.token}")
                        Log.e("hello", "Success${response.data!!.message.toString()}")
                        Log.e("hello", response.data!!.status.toString())

                        response.data.data.token.let { setUserToken(it) }

                        Toast.makeText(
                                requireActivity(),
                                getStringValue(TOKEN),
                                Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(R.id.action_loginFragment_to_homeFragment)
//                        navController.navigate(R.id.action_loginFragment_to_profileFragment)

                    }catch (e : Exception){
                        Log.e("Exception", e.message.toString())
                        Toast.makeText(
                                requireActivity(),
                                "Exception ${e.message.toString()}",
                                Toast.LENGTH_LONG
                        ).show()
                    }

                }
                is Resource.Error -> {
                    response.massage?.let {
                        Log.e("hello Error", response.massage.toString())
                        Log.e("hello Error", response.data!!.message.toString())
                        Log.e("hello Error", response.data!!.status.toString())

                        Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(activity, response.massage.toString(), Toast.LENGTH_LONG).show()
                    Toast.makeText(activity, response.data?.message, Toast.LENGTH_LONG).show()
                }

            }
        })

        binding.btnLogin.setOnClickListener() {

            var email = binding.tvEmail.text.toString()
            var password = binding.tvPassword.text.toString()

            loginRequest(LoginRequest(email, password))
        }

        binding.btnOrRegister.setOnClickListener() {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginRequest(loginRequest: LoginRequest) {
        viewModel.login(loginRequest)
    }

    private fun setUserToken(value: String) {
        editor.putString(TOKEN, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, EMPTY)
    }

}