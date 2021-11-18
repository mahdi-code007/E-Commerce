package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.databinding.ItemCategoriesBinding
import com.example.e_commerce.models.categories.CategoriesSubData


class CategoriesAdapter(onCategoryClick: OnItemClickListener) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    var _onCategoryClick = onCategoryClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
                ItemCategoriesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categories = differ.currentList[position]
        holder.bind(categories)
    }

    inner class CategoriesViewHolder(private val binding : ItemCategoriesBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(categories: CategoriesSubData) {


            binding.categories = categories
            binding.executePendingBindings()

            binding.categoriesCv.setOnClickListener() {
                _onCategoryClick.onCategoryClick(adapterPosition)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CategoriesSubData>() {
        override fun areItemsTheSame(oldItem: CategoriesSubData, newItem: CategoriesSubData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoriesSubData, newItem: CategoriesSubData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onCategoryClick(position: Int)
    }

}