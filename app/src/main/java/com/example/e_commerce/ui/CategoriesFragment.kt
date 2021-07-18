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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.adapters.CategoriesAdapter
import com.example.e_commerce.databinding.FragmentCategoriesBinding
import com.example.e_commerce.util.Constants
import com.example.e_commerce.util.Resource


class CategoriesFragment : Fragment(), CategoriesAdapter.OnItemClickListener {

    private final val TAG = "CategoriesTag"
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoriesBinding.inflate(inflater)
        setUpRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getCategories()
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

        viewModel.category_MLD.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    try {
                        Toast.makeText(
                            requireActivity(),
                            response.data?.data?.data?.size.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                        categoriesAdapter.differ.submitList(response.data?.data?.data)
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
        categoriesAdapter = CategoriesAdapter(this)
        binding.rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onCategoryClick(position: Int) {
        var id = categoriesAdapter.differ.currentList.get(position).id
        Log.i(TAG, "onCategoryClick: $id")
        Toast.makeText(requireActivity(), id.toString(), Toast.LENGTH_SHORT).show()
        val action =
            CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailsFragment(id)
        navController.navigate(action)
    }
}