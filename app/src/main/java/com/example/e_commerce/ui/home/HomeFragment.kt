package com.example.e_commerce.ui.home

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.adapters.BannersAdapter
import com.example.e_commerce.adapters.HomeCategoriesAdapter
import com.example.e_commerce.adapters.ProductsAdapter
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment(), BannersAdapter.OnItemClickListener,
    ProductsAdapter.OnItemClickListener, HomeCategoriesAdapter.OnItemClickListener {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var bannersAdapter: BannersAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var homeCategoriesAdapter: HomeCategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initSharedPreference()
        binding = FragmentHomeBinding.inflate(inflater)

        setUpRecyclerView()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        Log.i(TAG, "onCreateView: token ${getStringValue(Constants.TOKEN)}")
        try {
            getStringValue(Constants.TOKEN)?.let {
                viewModel.getHomeData(it)
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            Log.e(TAG, e.localizedMessage.toString())
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        binding.homeSearchEt.setOnClickListener() {
            navController.navigate(R.id.action_homeFragment_to_searchFragment)
        }


        viewModel.homeData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Resource.Success")
                    try {
                        response.data?.homeData?.banners.let {
                            Log.i(TAG, "Resource.Success banners")
//                            Log.i(TAG, response.data.toString())
                            bannersAdapter.differ.submitList(it)
                        }

                        response.data?.homeData?.productsData.let {
                            Log.i(TAG, "Resource.Success products")
                            Log.i(TAG, response.data.toString())
                            Log.i(TAG, it.toString())
                            productsAdapter.differ.submitList(it)
                        }

                        response.data?.homeData?.productsData.let {
                            it?.forEach {
                                Log.i(TAG, "onViewCreated: inFavorites ${it.toString()} ")
                            }
                        }

                        response.data?.homeData?.banners.let {
                            homeCategoriesAdapter.differ.submitList(it)
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


        viewModel.addToFavorites.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Resource.Success addToFavorites")
                    try {

                        response.data?.message.let {
                            Snackbar.make(requireView(), it.toString(), Snackbar.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        Log.i(TAG, e.message.toString())
                    }

                }
                is Resource.Error -> {
                    try {
                        response.data?.message?.let {
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


    private fun setUpRecyclerView() {
        bannersAdapter = BannersAdapter(this)
        binding.homeBannersRv.apply {
            adapter = bannersAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL))
        }
        //-----------------------------------------------------------------------------------------------------
        productsAdapter = ProductsAdapter(this)
        binding.homeProductsRv.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        //-----------------------------------------------------------------------------------------------------
        homeCategoriesAdapter = HomeCategoriesAdapter(this)
        binding.homeCategoryRv.apply {
            adapter = homeCategoriesAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(), LinearLayoutManager.HORIZONTAL, false
            )
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL))
        }

    }

    private fun initSharedPreference() {
        sharedPreferences = requireActivity().getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
    }

    private fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, Constants.EMPTY)
    }


    override fun onBannerClick(position: Int) {
        val categoryId = bannersAdapter.differ.currentList[position].category?.id
        val action = categoryId?.let {
            HomeFragmentDirections.actionHomeFragmentToCategoryDetailsFragment(
                it
            )
        }
        if (action != null) {
            navController.navigate(action)
        }
    }

    override fun onProductClick(position: Int) {
        val productId = productsAdapter.differ.currentList[position].id
        val productFav = productsAdapter.differ.currentList[position].inFavorites

        Snackbar.make(requireView(), productFav.toString(), Snackbar.LENGTH_LONG).show()
        getStringValue(Constants.TOKEN)?.let { viewModel.getProductDetails(it, productId) }
    }

    override fun onAddToFavoritesClick(productsId: Int) {
        getStringValue(Constants.TOKEN)?.let {
            viewModel.addToFavorites(it, productsId)
            viewModel.getHomeData(it)
        }
    }

    override fun onCategoryClick(position: Int) {
//        Toast.makeText(
//            requireContext(),
//            homeCategoriesAdapter.differ.currentList[position].category.name.toString(),
//            Toast.LENGTH_LONG
//        ).show()
        val categoryId = homeCategoriesAdapter.differ.currentList[position].category?.id
        val action = categoryId?.let {
            HomeFragmentDirections.actionHomeFragmentToCategoryDetailsFragment(
                it
            )
        }
        if (action != null) {
            navController.navigate(action)
        }
    }


}

