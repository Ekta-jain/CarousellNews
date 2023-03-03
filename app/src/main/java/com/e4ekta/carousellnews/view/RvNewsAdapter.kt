package com.e4ekta.carousellnews.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.carousellnews.databinding.ItemNewsArticleBinding
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem


class RvNewsAdapter : ListAdapter<CarousellNewsResponseItem, RvNewsAdapter.ViewHolder>(
    TaskDiffCallBack()
) {


    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of item.xml
    // ie ItemNewsArticleBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: ItemNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(t: CarousellNewsResponseItem) {
            with(currentList[adapterPosition]) {
                binding.response = this
            }
        }
    }

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // bind the items with each item of the list languageList which than will be
    // shown in recycler view
    // to keep it simple we are not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    // return the size of currentList
    override fun getItemCount(): Int {
        return currentList.size
    }

    //This check runs on background thread
    class TaskDiffCallBack : DiffUtil.ItemCallback<CarousellNewsResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CarousellNewsResponseItem,
            newItem: CarousellNewsResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CarousellNewsResponseItem,
            newItem: CarousellNewsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
