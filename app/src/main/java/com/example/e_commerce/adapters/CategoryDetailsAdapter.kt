package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.databinding.ItemCategoryDetailsBinding
import com.example.e_commerce.models.categories.CategoriesDetailsSubData


class CategoryDetailsAdapter(onCategoryClick: OnItemClickListener) :
    RecyclerView.Adapter<CategoryDetailsAdapter.CategoryDetailsViewHolder>() {

    var _onCategoryClick = onCategoryClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDetailsViewHolder {


        return CategoryDetailsViewHolder(
            ItemCategoryDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryDetailsViewHolder, position: Int) {
        val categories = differ.currentList[position]
        holder.bind(categories)
    }

    inner class CategoryDetailsViewHolder(private val binding: ItemCategoryDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesDetails: CategoriesDetailsSubData) {
            binding.categoryDetails = categoriesDetails
            binding.executePendingBindings()

            binding.productCv.setOnClickListener() {
                _onCategoryClick.onCategoryClick(adapterPosition)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CategoriesDetailsSubData>() {
        override fun areItemsTheSame(
            oldItem: CategoriesDetailsSubData,
            newItem: CategoriesDetailsSubData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoriesDetailsSubData,
            newItem: CategoriesDetailsSubData
        ): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onCategoryClick(position: Int)
    }

}