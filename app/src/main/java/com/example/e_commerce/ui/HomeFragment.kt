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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.adapters.BannersAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.models.home.homeData.Banner
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource


class HomeFragment : Fragment(), BannersAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    lateinit var viewModel: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var bannersAdapter: BannersAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)
        setUpRecyclerView()
        //test
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        sharedPreferences = requireContext().getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()

        try {
//            token = getStringValue(Constants.TOKEN)
            loadBanners(getStringValue(Constants.TOKEN))

        } catch (e: Exception) {
            Log.e("Home", e.message.toString())
            Log.e("Home", e.localizedMessage.toString())
        }



        viewModel.homeData_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {

                    try {
                        response.data?.data?.banners?.let { bannerResponse ->
                            Log.e("Home Success", bannerResponse.size.toString())
//                            bannersAdapter.differ.submitList(bannerResponse)
                        }
                    } catch (e: Exception) {
                        Log.e("Home Success", e.message.toString())
                    }

                }
                is Resource.Error -> {
                    try {
                        response.data?.message?.let {
//                            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                            Log.e("Home Error", it.toString())
                        }
                    } catch (e: Exception) {
                        Log.e("Home Error", e.message.toString())
                        Log.e("Home Error", e.localizedMessage.toString())
                    }

                }
                is Resource.Loading -> {

                }
            }
        })

    }

    private fun loadBanners(token: String?) {
        viewModel.getHomeData(token)
    }

    private fun setUpRecyclerView() {
        bannersAdapter = BannersAdapter(this)
        binding.rvHomeBanner.apply {
            adapter = bannersAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL))
        }
    }

    private fun setUserToken(value: String) {
        editor.putString(Constants.TOKEN, value)
        editor.apply()
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, Constants.EMPTY)
    }

    override fun onBannerClick(banner: Banner) {
//        Toast.makeText(activity , "click ${banner.category?.name}" , Toast.LENGTH_LONG).show()
    }

}