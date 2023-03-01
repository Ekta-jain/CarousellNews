package com.e4ekta.carousellnews.viewmodel

import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import com.e4ekta.network_module.src.utils.Resource
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    // Create a mock for MainRepository
    @Mock
    lateinit var mainRepository: MainRepository

    // Create an instance of MainViewModel
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        // Initialize the MainViewModel with the mocked MainRepository
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun `test fetchData() with successful response`() = runBlockingTest {
        // Mock the response of the fetchCustomUI() method
        val mockResponse = Resource.success(ArrayList<CarousellNewsResponseItem>())
        Mockito.`when`(mainRepository.fetchCustomUI()).thenReturn(mockResponse)

        // Call the fetchData() method
        mainViewModel.fetchData()

        // Verify that the _myStateFlow value is updated with the mock response
        val stateFlowValue = mainViewModel.myStateFlow.value
        Assert.assertEquals(mockResponse, stateFlowValue)
    }

    @Test
    fun `test fetchData() with error response`() = runBlockingTest {
        // Mock the response of the fetchCustomUI() method with an error message
        val errorMessage = "Error fetching data"
        val mockResponse = Resource.error<ArrayList<CarousellNewsResponseItem>>(errorMessage)
        Mockito.`when`(mainRepository.fetchCustomUI()).thenReturn(mockResponse)

        // Call the fetchData() method
        mainViewModel.fetchData()

        // Verify that the _myStateFlow value is updated with the error message
        val stateFlowValue = mainViewModel.myStateFlow.value
        Assert.assertEquals(mockResponse, stateFlowValue)
    }
}
