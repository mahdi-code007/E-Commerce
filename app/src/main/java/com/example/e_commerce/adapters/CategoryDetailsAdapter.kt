package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemCategoryDetailsBinding
import com.example.e_commerce.models.ProductOld


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
        val categoriesDetails = differ.currentList[position]
        if (categoriesDetails.inFavorites){
            holder.binding.homeFavoriteBtn.setImageResource(R.drawable.in_favorite)
        }
        holder.bind(categoriesDetails)

    }

    inner class CategoryDetailsViewHolder(val binding: ItemCategoryDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesDetails: ProductOld) {
            binding.categoryDetails = categoriesDetails
            binding.executePendingBindings()



            binding.productCv.setOnClickListener() {
                _onCategoryClick.onCategoryClick(adapterPosition)
            }

            binding.homeFavoriteBtn.setOnClickListener(){
                _onCategoryClick.onAddToFavoritesClick(categoriesDetails.id)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ProductOld>() {
        override fun areItemsTheSame(
            oldItem: ProductOld,
            newItem: ProductOld
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductOld,
            newItem: ProductOld
        ): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onCategoryClick(position: Int)
        fun onAddToFavoritesClick(productsId: Int)
    }

}