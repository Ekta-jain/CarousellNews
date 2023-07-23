package com.e4ekta.carousellnews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e4ekta.carousellnews.R
import com.e4ekta.carousellnews.databinding.ActivityMainBinding
import com.e4ekta.carousellnews.viewmodel.FilterOptions
import com.e4ekta.carousellnews.viewmodel.MainViewModel
import com.e4ekta.network_module.src.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity() : BaseActivity<ActivityMainBinding>() {
    private lateinit var mainViewModel: MainViewModel
   // private lateinit var binding: ActivityMainBinding
    //private lateinit var rvNewsAdapter: RvNewsAdapter

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
      //  binding.toolbar.overflowIcon = getDrawable(R.drawable.option_menu)
     //   setSupportActionBar(binding.toolbar)
        //v2
        views.toolbar.overflowIcon = getDrawable(R.drawable.option_menu)
        setSupportActionBar(views.toolbar)
       // views.newsList = it.data
       // setUpAdapter()


        setObserver()  //new approach
    }

    private fun setUpAdapter() {
//        views.recycleNewsList.layoutManager =
//            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        rvNewsAdapter = RvNewsAdapter()
//        views.recycleNewsList.adapter = rvNewsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popular -> {
                mainViewModel.sortBy(FilterOptions.POPULAR)
                false
            }
            R.id.action_recent -> {
                mainViewModel.sortBy(FilterOptions.RECENT)
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            mainViewModel.uiState.collect {
            //    rvNewsAdapter.submitList(it.data)
                Toast.makeText(this@MainActivity,""+it.data?.size, Toast.LENGTH_LONG).show()
                views.newsList = it.data

            }
        }
    }
}