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
    private lateinit var mainViewModelV2: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvNewsAdapter: RvNewsAdapter
    private var newsMutableList: MutableList<CarousellNewsResponseItem> = mutableListOf()
    private var sortByRank = false
    // get reference to the adapter class
   // private val rvAdapter by lazy { RvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModelV2 = ViewModelProvider(this)[MainViewModel::class.java]
        binding.toolbar.overflowIcon = getDrawable(R.drawable.option_menu)
        setSupportActionBar(binding.toolbar)

        setUpAdapter()
        setObserver()
        newsMutableList.addAll(rvNewsAdapter.currentList)
    }


    private fun setUpAdapter() {
        binding.recycleNewsList.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false);
        rvNewsAdapter = RvNewsAdapter()
        binding.recycleNewsList.adapter = rvNewsAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popular -> {
                newsMutableList.addAll(rvNewsAdapter.currentList)
                Toast.makeText(this,"Popular clicked", Toast.LENGTH_SHORT).show()
                newsMutableList.sortWith { obj1, obj2 ->
                    // To compare integer values
                    obj1.rank.compareTo(obj2.rank)
                }
                rvNewsAdapter.submitList(newsMutableList.toList())
                false
            }
            R.id.action_recent -> {
               // newsMutableList.addAll(rvNewsAdapter.currentList)
                Toast.makeText(this,"Recent clicked", Toast.LENGTH_SHORT).show()
                newsMutableList.sortWith { obj1, obj2 ->
                    // To compare integer values
                    obj1.timeCreated.compareTo(obj2.timeCreated)
                }
                rvNewsAdapter.submitList(newsMutableList.toList())
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setObserver() {
        lifecycleScope.launch {
            mainViewModelV2.myStateFlow.collect {
                rvNewsAdapter.submitList(it.data)
            }
        }

    }


}