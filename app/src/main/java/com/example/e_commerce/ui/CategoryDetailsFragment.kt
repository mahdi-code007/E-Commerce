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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce.adapters.CategoryDetailsAdapter
import com.example.e_commerce.databinding.FragmentCategoryDetailsBinding
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource

class CategoryDetailsFragment : Fragment(), CategoryDetailsAdapter.OnItemClickListener {

    private final val TAG = "CategoryDetailsTag"
    private lateinit var binding: FragmentCategoryDetailsBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var categoryDetailsAdapter: CategoryDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryDetailsBinding.inflate(inflater)


        setUpRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (arguments != null){
            val args = CategoryDetailsFragmentArgs.fromBundle(requireArguments())
            viewModel.getCategoryDetails(args.categoryId)
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

        viewModel.categoryDetails_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    try {
                        Toast.makeText(
                            requireActivity(),
                            response.data?.data?.data?.size.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                        categoryDetailsAdapter.differ.submitList(response.data?.data?.data)
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
    }

    private fun setUpRecyclerView() {
        categoryDetailsAdapter = CategoryDetailsAdapter(this)
        binding.categoryDetailsRv.apply {
            adapter = categoryDetailsAdapter
            layoutManager = GridLayoutManager(activity , 2)
//            addItemDecoration(DividerItemDecoration(activity, GridLayoutManager.VERTICAL))
        }
    }

    override fun onCategoryClick(position: Int) {
        val product_id = categoryDetailsAdapter.differ.currentList.get(position).id
        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
    }

}