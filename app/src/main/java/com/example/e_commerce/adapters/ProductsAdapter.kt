package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.databinding.ItemProductsBinding
import com.example.e_commerce.models.home.homeData.Product


class ProductsAdapter(onProductClick: OnItemClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    var _onProductClick = onProductClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {


        return ProductsViewHolder(
            ItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val products = differ.currentList[position]
        holder.bind(products)
    }

    inner class ProductsViewHolder(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Product) {
            binding.products = products
            binding.executePendingBindings()
//            Glide.with(binding.root).load(products.image).into(binding.productImageIv)
//            binding.productNameIv.text = products.name

            binding.root.setOnClickListener() {
                _onProductClick.onProductClick(products)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onProductClick(product: Product)
    }

}