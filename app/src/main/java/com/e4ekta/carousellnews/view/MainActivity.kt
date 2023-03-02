package com.e4ekta.carousellnews.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.carousellnews.viewmodel.MainViewModel
import com.e4ekta.carousellnews.R
import com.e4ekta.carousellnews.databinding.ActivityMainBinding
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvNewsAdapter: RvNewsAdapter
    private var newsMutableList: MutableList<CarousellNewsResponseItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.toolbar.overflowIcon = getDrawable(R.drawable.option_menu)
        setSupportActionBar(binding.toolbar)

        setUpAdapter()
        setObserver()
    }

    private fun setUpAdapter() {
        binding.recycleNewsList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvNewsAdapter = RvNewsAdapter()
        binding.recycleNewsList.adapter = rvNewsAdapter
        mainViewModel.fetchData()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popular -> {
                newsMutableList.addAll(rvNewsAdapter.currentList)
                Toast.makeText(this,"Popular clicked"+newsMutableList.size, Toast.LENGTH_SHORT).show()
                rvNewsAdapter.submitList( mainViewModel.sortNewsListByRank(newsMutableList))
                false
            }
            R.id.action_recent -> {
                if(newsMutableList.isEmpty()){
                    newsMutableList.addAll(rvNewsAdapter.currentList)
                }
                Toast.makeText(this,"Recent clicked", Toast.LENGTH_SHORT).show()
                rvNewsAdapter.submitList( mainViewModel.sortNewsListByTimeCreated(newsMutableList))
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            mainViewModel.myStateFlow.collect {
                rvNewsAdapter.submitList(it.data)
            }
        }
    }
}