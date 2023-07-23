package com.e4ekta.carousellnews.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.carousellnews.view.RvNewsAdapter
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem

@BindingAdapter("news_list")
fun RecyclerView.bindNewsList(newsList : ArrayList<CarousellNewsResponseItem>?){
    val adapter: RvNewsAdapter = getNewsAdapter(this)
    adapter.submitList(newsList)
}

fun getNewsAdapter(recyclerView: RecyclerView): RvNewsAdapter {
return if (recyclerView.adapter != null && recyclerView.adapter is RvNewsAdapter){
    recyclerView.adapter as RvNewsAdapter
}else{
    val adapter = RvNewsAdapter()
    recyclerView.adapter = adapter
    adapter
}
}


