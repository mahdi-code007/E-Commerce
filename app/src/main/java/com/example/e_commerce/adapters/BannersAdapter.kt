package com.example.e_commerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.databinding.ItemBannersBinding
import com.example.e_commerce.models.home.homeData.Banner


class BannersAdapter(onBannerClick: OnItemClickListener) : RecyclerView.Adapter<BannersAdapter.BannersViewHolder>() {

    var _onBannerClick = onBannerClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannersViewHolder {
        return BannersViewHolder(
                ItemBannersBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BannersViewHolder, position: Int) {
        val banner = differ.currentList[position]
        holder.bind(banner)
    }

    inner class BannersViewHolder(private val binding: ItemBannersBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner) {
            Glide.with(binding.root).load(banner.image).into(binding.ivBanner)
            binding.root.setOnClickListener() {
                _onBannerClick.onBannerClick(banner)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    interface OnItemClickListener {
        fun onBannerClick(banner: Banner)
    }

}