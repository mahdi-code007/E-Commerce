package com.example.e_commerce.ui.favorites

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce.adapters.FavoritesAdapter
import com.example.e_commerce.databinding.FragmentFavoriteBinding
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment(), FavoritesAdapter.OnItemClickListener {
    private val TAG = "FavoritesFragment"

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initSharedPreference()

        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        setUpRecyclerView()


        try {
            getStringValue(Constants.TOKEN)?.let {
                favoritesViewModel.getFavorites(it)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        favoritesViewModel.favoritesData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Resource.Success favoritesData")
                    try {
                        response.data?.favoritesData?.favoritesSubData.let {
                            favoritesAdapter.differ.submitList(it)
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
                    }

                }
                is Resource.Loading -> {

                }
            }
        })

        favoritesViewModel.addToFavorites.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Resource.Success addToFavorites")
                    try {

                        response.data?.message.let {
                            Snackbar.make(requireView(), it.toString(), Snackbar.LENGTH_LONG).show()
                        }
                        getStringValue(Constants.TOKEN)?.let {
                            favoritesViewModel.getFavorites(it)
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

        favoritesAdapter = FavoritesAdapter(this)
        binding.favoritesRv.apply {
            adapter = favoritesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
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

    override fun onProductClick(position: Int) {

    }

    override fun onAddToFavoritesClick(productsId: Int) {
        getStringValue(Constants.TOKEN)?.let {
            favoritesViewModel.addToFavorites(it, productsId)
        }
    }
}




