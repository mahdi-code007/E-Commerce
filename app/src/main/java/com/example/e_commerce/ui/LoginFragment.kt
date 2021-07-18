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
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Constants.Companion.EMPTY
import com.example.e_commerce.util.Constants.Companion.SHARED_PREFERENCES_NAME
import com.example.e_commerce.util.Constants.Companion.TOKEN
import com.example.e_commerce.util.Resource


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        sharedPreferences =
            requireContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
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
                        saveLogin(Constants.IS_LOGIN)
                        navController.navigate(R.id.action_loginFragment_to_homeFragment)
//                        navController.navigate(R.id.action_loginFragment_to_profileFragment)

                    } catch (e: Exception) {
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
                    binding.progressBarLogin.visibility = View.VISIBLE
                }

            }
        })

        binding.btnLogin.setOnClickListener() {

            var email = binding.loginEmailEt.text.toString()
            var password = binding.loginPasswordIlEt.text.toString()
            if (email.isEmpty()) {
                binding.loginEmailEt.error = "Pales enter your email"
                binding.loginEmailEt.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.loginPasswordIlEt.error = "Pales enter your password"
                binding.loginPasswordIlEt.requestFocus()
                return@setOnClickListener
            }
            loginRequest(LoginRequest(email, password))
        }

        binding.btnOrRegister.setOnClickListener() {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()
//        if (!getStringValue(Constants.TOKEN).equals("null")){
//            navController.navigate(R.id.action_loginFragment_to_homeFragment)
//        }
        if (isLogin(Constants.IS_LOGIN) == true){
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        }

    }


    private fun loginRequest(loginRequest: LoginRequest) {
        viewModel.login(loginRequest)
    }

    private fun setUserToken(value: String) {
        editor.putString(TOKEN, value)
        editor.apply()
    }

    private fun isLogin(key: String) : Boolean?{
        return sharedPreferences.getBoolean(key , false)
    }

    private fun saveLogin(key: String){
        editor.putBoolean(key , true)
    }

    private fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, EMPTY)
    }

}