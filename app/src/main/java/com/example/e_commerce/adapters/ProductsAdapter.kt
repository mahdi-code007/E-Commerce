package com.example.e_commerce.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ItemProductsBinding
import com.example.e_commerce.models.ProductOld


class ProductsAdapter(_onProductClick: OnItemClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    var onProductClick = _onProductClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {


        return ProductsViewHolder(
            ItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount() = differ.currentList.size


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = differ.currentList[position]
        if (products.inFavorites){
            Log.i("ProductsAdapter", "onBindViewHolder: ${products.name}")
            holder.binding.homeFavoriteBtn.setImageResource(R.drawable.in_favorite)

        }
        else{
            holder.binding.homeFavoriteBtn.setImageResource(R.drawable.ic_favorite_border)
        }
        holder.bind(products)


    }



    inner class ProductsViewHolder(val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(products: ProductOld) {
            binding.products = products
            binding.executePendingBindings()

            binding.homeProductsCv.setOnClickListener() {
                onProductClick.onProductClick(adapterPosition)
            }
            binding.homeFavoriteBtn.setOnClickListener(){
                onProductClick.onAddToFavoritesClick(products.id)
            }
            val state = differ.currentList[adapterPosition].inFavorites
            Log.i("onBindViewHolder", "onBindViewHolder: ${state.toString()}")
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ProductOld>() {
        override fun areItemsTheSame(oldItem: ProductOld, newItem: ProductOld): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductOld, newItem: ProductOld): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onProductClick(position: Int)
        fun onAddToFavoritesClick(productsId: Int)
    }

}