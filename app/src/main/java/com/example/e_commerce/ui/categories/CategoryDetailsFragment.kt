package com.example.e_commerce.ui.categories

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce.adapters.CategoryDetailsAdapter
import com.example.e_commerce.databinding.FragmentCategoryDetailsBinding
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource
import com.google.android.material.snackbar.Snackbar

class CategoryDetailsFragment : Fragment(), CategoryDetailsAdapter.OnItemClickListener {

    private final val TAG = "CategoryDetailsTag"
    private lateinit var binding: FragmentCategoryDetailsBinding
    private lateinit var navController: NavController
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var categoryDetailsAdapter: CategoryDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryDetailsBinding.inflate(inflater)

        initSharedPreference()

        setUpRecyclerView()

        categoriesViewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)

        getArgsAndCategoryDetails()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        categoriesViewModel.categoryDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    try {
                        Toast.makeText(
                            requireActivity(),
                            response.data?.parentData?.productList?.size.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                        categoryDetailsAdapter.differ.submitList(response.data?.parentData?.productList)
                    } catch (e: Exception) {

                    }
                }
                is Resource.Error -> {
                    try {

                    } catch (e: Exception) {
                        Log.i(TAG, "Resource.Error -> ${e.localizedMessage}")
                    }
                }

                is Resource.Loading -> {
                    try {

                    } catch (e: Exception) {
                        Log.i(TAG, "Resource.Loading -> ${e.localizedMessage} ")
                    }
                }
            }

        })

        categoriesViewModel.addToFavorites.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i(TAG, "Resource.Success addToFavorites")
                    try {
                        response.data?.message.let {
                            Snackbar.make(requireView(), it.toString(), Snackbar.LENGTH_LONG).show()
                        }
                        getArgsAndCategoryDetails()

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
        categoryDetailsAdapter = CategoryDetailsAdapter(this)
        binding.categoryDetailsRv.apply {
            adapter = categoryDetailsAdapter
            layoutManager = GridLayoutManager(activity, 2)
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


    private fun getArgsAndCategoryDetails() {
        if (arguments != null) {
            var args = CategoryDetailsFragmentArgs.fromBundle(requireArguments())
            categoriesViewModel.getCategoryDetails(args.categoryId)
        }
    }

    override fun onCategoryClick(position: Int) {
        val productId = categoryDetailsAdapter.differ.currentList.get(position).id
        Toast.makeText(requireContext(), productId, Toast.LENGTH_SHORT).show()
    }

    override fun onAddToFavoritesClick(productsId: Int) {
        Toast.makeText(requireActivity(), productsId.toString(), Toast.LENGTH_SHORT).show()
        getStringValue(Constants.TOKEN)?.let {
            categoriesViewModel.addToFavorites(it, productsId)
        }
    }

}