package com.e4ekta.carousellnews.view

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.e4ekta.carousellnews.databinding.ActivityMainBinding
import com.e4ekta.carousellnews.viewmodel.MainViewModel
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    @Mock
    lateinit var viewModel: MainViewModel

    private lateinit var activity: MainActivity
    private lateinit var binding: ActivityMainBinding

    @Before
    fun setUp() {
        activity = spy(MainActivity())
        binding = spy(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.main, null, false))
        doReturn(binding).`when`(activity).setContentView(R.layout.activity_main)
        doReturn(viewModel).`when`(activity).getViewModel()
        activity.onCreate(null)
    }

    @Test
    fun testOptionsMenu() {
        val menu = mock(Menu::class.java)
        val item1 = mock(MenuItem::class.java)
        val item2 = mock(MenuItem::class.java)
        doReturn(R.id.action_popular).`when`(item1).itemId
        doReturn(R.id.action_recent).`when`(item2).itemId
        doReturn(true).`when`(activity).onCreateOptionsMenu(menu)
        doReturn(false).`when`(activity).onOptionsItemSelected(item1)
        doReturn(false).`when`(activity).onOptionsItemSelected(item2)
        activity.onCreateOptionsMenu(menu)

        //Test sort by rank
        doReturn(mutableListOf(CarousellNewsResponseItem(rank = 2), CarousellNewsResponseItem(rank = 1))).`when`(viewModel).getNewsItems()
        activity.onOptionsItemSelected(item1)
        val sortedList = activity.rvNewsAdapter.currentList
        assertTrue(sortedList[0].rank < sortedList[1].rank)

        //Test sort by recent
        doReturn(mutableListOf(CarousellNewsResponseItem(timeCreated = "2022-02-28T20:51:06.901+08:00"), CarousellNewsResponseItem(timeCreated = "2022-02-28T20:50:06.901+08:00"))).`when`(viewModel).getNewsItems()
        activity.onOptionsItemSelected(item2)
        val sortedRecentList = activity.rvNewsAdapter.currentList
        assertTrue(sortedRecentList[0].timeCreated > sortedRecentList[1].timeCreated)
    }
}
