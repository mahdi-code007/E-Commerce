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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.adapters.BannersAdapter
import com.example.e_commerce.adapters.ProductsAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.models.home.homeData.Banner
import com.example.e_commerce.models.home.homeData.Product
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource


class HomeFragment : Fragment(), BannersAdapter.OnItemClickListener,
    ProductsAdapter.OnItemClickListener {

    private final val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var bannersAdapter: BannersAdapter
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)


        setUpRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        try {
            loadBanners(Constants.TOKEN)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            Log.e(TAG, e.localizedMessage.toString())
        }

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

        viewModel.homeData_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Resource.Success")
                    try {
                        response.data?.data?.banners.let {
                            Log.i(TAG, "Resource.Success 22")
                            Log.e(TAG, response.data.toString())
                            bannersAdapter.differ.submitList(it)
                        }

                        response.data?.data?.products.let {
                            Log.i(TAG, "Resource.Success products")
                            Log.e(TAG, response.data.toString())
                            productsAdapter.differ.submitList(it)
                        }


                    } catch (e: Exception) {
                        Log.i(TAG, e.message.toString())
                    }

                }
                is Resource.Error -> {
                    try {
                        response.data?.message?.let {
//                            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                            Log.i(TAG, it.toString())
                        }
                    } catch (e: Exception) {
                        Log.i(TAG, e.message.toString())
                        Log.i(TAG, e.localizedMessage.toString())
                    }

                }
                is Resource.Loading -> {

                }
            }
        })


    }

    private fun loadBanners(token: String) {
        viewModel.getHomeData(token)
    }



    private fun setUpRecyclerView() {
        bannersAdapter = BannersAdapter(this)
        binding.rvHomeBanner.apply {
            adapter = bannersAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL))
        }

        productsAdapter = ProductsAdapter(this)

        binding.rvHomeProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(requireContext() , 2)
        }
    }

    private fun setUserToken(value: String) {
        editor.putString(Constants.TOKEN, value)
        editor.apply()
    }

    private fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, Constants.EMPTY)
    }


    override fun onBannerClick(banner: Banner) {
        Toast.makeText(requireContext(), banner.id.toString(), Toast.LENGTH_LONG).show()
        Toast.makeText(requireContext(), banner.category?.name, Toast.LENGTH_LONG).show()
    }

    override fun onProductClick(product: Product) {
        Toast.makeText(requireContext(), product.id.toString(), Toast.LENGTH_LONG).show()
        Toast.makeText(requireContext(), product.name, Toast.LENGTH_LONG).show()
    }


}

