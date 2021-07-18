package com.example.e_commerce.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.e_commerce.databinding.FragmentProfileBinding
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Constants.Companion.TOKEN
import com.example.e_commerce.util.Resource


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater)
//        sharedPreference.getString("token","null")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        sharedPreferences = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        getStringValue(TOKEN)?.let { viewModel.getProfileData(it) }

        viewModel.prfile_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.e("test", response.massage.toString())
                    Log.e("test", "Success ${response.data!!.message}")
                    Log.e("test", response.data!!.status.toString())
//                    response.data.profileData.image?.let { Log.e("test", it) }
//                    Toast.makeText(
//                            requireActivity(),
//                            "Resource.Success : ${response.massage.toString()}",
//                            Toast.LENGTH_LONG
//                    ).show()
                    try {
                        Glide.with(requireActivity()).load(response.data.data.image).into(binding.ivUserImageProfile)
                        binding.tvNameProfile.text = response.data.data.name.toString()
                        binding.tvEmailProfile.text = response.data.data.email.toString()
                        binding.tvPhoneProfile.text = response.data.data.phone.toString()
                        binding.tvPointsProfile.text = response.data.data.points.toString()
                        binding.tvCreditProfile.text = response.data.data.credit.toString()
                    }catch (e : Exception){
                        Log.e("test", "Exception : ${e.message}")
                    }

                }
                is Resource.Error -> {
                    response.massage?.let {
                        Log.e("test", "Resource.Error : ${response.massage.toString()}")
                        Log.e("test", "Resource.Error : ${response.data!!.message}")
                        Log.e("test", "Resource.Error : ${response.data.status.toString()}")
//                        Toast.makeText(activity, "Resource.Error : ${it}", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
//                    Toast.makeText(activity, response.massage.toString(), Toast.LENGTH_LONG).show()
//                    Toast.makeText(requireActivity(), response.data?.message, Toast.LENGTH_LONG).show()
                }
            }
        })


    }
    private fun setUserToken(value: String) {
        editor.putString(Constants.TOKEN, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, Constants.EMPTY)
    }


}