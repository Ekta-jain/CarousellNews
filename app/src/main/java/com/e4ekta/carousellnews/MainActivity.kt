package com.e4ekta.carousellnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.carousellnews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModelV2: MainViewModelV2
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvNewsAdapter: RvNewsAdapter
    private lateinit var myAdapter: MyAdapter
    // get reference to the adapter class
   // private val rvAdapter by lazy { RvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
       // setContentView(R.layout.activity_main)
        mainViewModelV2 = ViewModelProvider(this)[MainViewModelV2::class.java]

        binding.recycleNewsList.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false);
        //binding.recycleNewsList.hasFixedSize()
        // attach adapter to the recycler view
        rvNewsAdapter = RvNewsAdapter()
        myAdapter = MyAdapter()
        binding.recycleNewsList.adapter = rvNewsAdapter
        setObserver()
       // myAdapter.submitList(it.data)

        // create new objects

        // call set data method of adapter class and the list data

//        runBlocking {
//
//        }

    }
    private fun setObserver() {
        //
        lifecycleScope.launch {
            mainViewModelV2.myStateFlow.collect {
                Log.i("Observe", "::" + it.data)
                //delay(2000)
                rvNewsAdapter.submitList(it.data)
                //  binding.recycleNewsList.adapter = rvAdapter
            }
        }

    }

}