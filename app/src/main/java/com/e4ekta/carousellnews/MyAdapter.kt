package com.e4ekta.carousellnews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem

class MyAdapter : ListAdapter<CarousellNewsResponseItem, MyViewHolder>(MyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_article, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: CarousellNewsResponseItem) {
        Log.i("ItemData","="+item)
        // Bind the data to the views
//        with(currentList[position]){
//            binding.tvHeader.text = this.title
//            binding.tvSubHeader.text = this.description
//            com.bumptech.glide.Glide.with(binding.root.context)
//                .load(this.bannerUrl)
//                .into(binding.imageHeader)
//        }
    }
}

class MyDiffCallback : DiffUtil.ItemCallback<CarousellNewsResponseItem>() {
    override fun areItemsTheSame(oldItem: CarousellNewsResponseItem, newItem: CarousellNewsResponseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CarousellNewsResponseItem, newItem: CarousellNewsResponseItem): Boolean {
        return oldItem == newItem
    }
}